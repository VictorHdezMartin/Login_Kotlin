package com.example.login_kotlin.utils

import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

import androidx.credentials.CredentialManager


class GoogleSingIn (
    private val context: Context
) {
    private val tag = "GoogleSingInClient: "

 //   private val credentialManager = android.credentials.CredentialManager.create(context)
    val credentialManager = CredentialManager.create(context)
    private val firebaseAuth = FirebaseAuth.getInstance()

    private fun isSignIn ():Boolean {
        return firebaseAuth.currentUser != null
    }

    suspend fun signIn (): Boolean {
        if (isSignIn()) {
            return true
        }

        try {
            Log.d(tag, "buildCredentialRequest")
            val result = buildCredentialRequest()
            return handleSingIn(result)

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw
            return false
        }
        return false
    }

    private suspend fun handleSingIn(result: GetCredentialResponse): Boolean {

        val credential = result.credential

        if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {

            try {
                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                println(tokenCredential.displayName)
                println(tokenCredential.id)
                println(tokenCredential.profilePictureUri)

                val authCredential = GoogleAuthProvider.getCredential(
                    tokenCredential.idToken, null
                )

                val authResult = firebaseAuth.signInWithCredential(authCredential).await()

                return  authResult.user != null

            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(tag, "handleSingIn:error")
                return false
            }

        } else {
            return false;
        }
    }

    private suspend fun buildCredentialRequest (): GetCredentialResponse {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(
                        "1056468059363-ktjtunlc51nelmmn8ffvg0326tthnegq.apps.googleusercontent.com"
                    )
                    .setAutoSelectEnabled(false)
                    .build()
            )
            .build()

        return credentialManager.getCredential(
            request = request, context = context
        )
    }

    suspend fun signOut() {
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
        firebaseAuth.signOut()
    }
}