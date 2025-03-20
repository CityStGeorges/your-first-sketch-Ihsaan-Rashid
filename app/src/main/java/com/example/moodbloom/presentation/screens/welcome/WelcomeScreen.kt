package com.example.moodbloom.presentation.screens.welcome

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodbloom.MainViewModel
import com.example.moodbloom.R
import com.example.moodbloom.extension.ResponseStates
import com.example.moodbloom.extension.SpacerHeight
import com.example.moodbloom.extension.SpacerWeight
import com.example.moodbloom.presentation.components.HandleApiStates
import com.example.moodbloom.presentation.components.LoginWithGoogleButton
import com.example.moodbloom.presentation.components.PromptTypeShow
import com.example.moodbloom.presentation.components.PromptsViewModel
import com.example.moodbloom.presentation.components.ResourceImage
import com.example.moodbloom.presentation.components.ScreenContainer
import com.example.moodbloom.presentation.components.TextButton
import com.example.moodbloom.presentation.components.hpr
import com.example.moodbloom.presentation.components.safeClickable
import com.example.moodbloom.presentation.components.sdp
import com.example.moodbloom.presentation.components.textSdp
import com.example.moodbloom.routes.ScreensRoute
import com.example.moodbloom.ui.typo.HeadlineMediumText
import com.example.moodbloom.ui.typo.LabelMediumText
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser



@Composable
fun WelcomeScreenRoute(
    onNavigate: (String) -> Unit,
    mainViewModel: MainViewModel,
    viewModel: WelcomeViewModel = hiltViewModel()
) {

    val userState by viewModel.userState.collectAsStateWithLifecycle()
    WelcomeScreen(onLoginSuccess = {
        mainViewModel.firebaseUser = it
        viewModel.clearState()
        onNavigate(ScreensRoute.Home.route)
    }, userState = userState,
        onGoogleSignInRequest = viewModel::googleSignIn,
        onNavigate=onNavigate)
}

@Composable
internal fun WelcomeScreen(
    promptsViewModel: PromptsViewModel = hiltViewModel(),
    userState: ResponseStates<FirebaseUser?> = ResponseStates.Idle,
    onNavigate: (String) -> Unit = {},
    onGoogleSignInRequest: (String) -> Unit = {},
    onLoginSuccess: (FirebaseUser) -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity

    val googleSignInClient = GoogleSignIn.getClient(
        activity,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id)) // Use Web Client ID here
            .requestEmail()
            .build()
    )
    //val googleSignInClient = GoogleSignIn.getClient(activity, GoogleSignInOptions.DEFAULT_SIGN_IN)
    val googleSignInLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.result
                account?.idToken?.let {onGoogleSignInRequest(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    val currentPrompt by promptsViewModel.currentPrompt.collectAsStateWithLifecycle()
    ScreenContainer(currentPrompt = currentPrompt) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpacerHeight(7.hpr)
            ResourceImage(image = R.drawable.ic_logo, modifier = Modifier.height(12.hpr))
            SpacerHeight(5.hpr)
            HeadlineMediumText(text = "Welcome", fontSize = 48.textSdp)
            SpacerWeight(.5f)
            TextButton(
                modifier = Modifier.padding(horizontal = 10.sdp),
                text = "Sign Up",
                onClick = {
                    onNavigate(ScreensRoute.SignUp.route)
                })
            SpacerHeight(2.hpr)
            TextButton(text = "Sign In",modifier = Modifier.padding(horizontal = 10.sdp), onClick = {
                    onNavigate(ScreensRoute.Login.route)
                })
            SpacerHeight(2.hpr)
            LoginWithGoogleButton(
                modifier = Modifier.padding(horizontal = 10.sdp),
                text = "Sign in with Google", onClick = {
                    googleSignInLauncher.launch(googleSignInClient.signInIntent)
                })
            SpacerHeight(4.hpr)
            LabelMediumText(text = "Forgot Your Password?", modifier = Modifier.safeClickable {
                onNavigate(ScreensRoute.ForgetPassword.route)
            })
            SpacerWeight(1f)
        }
    }
    HandleApiStates(
        state = userState,
        updatePrompt = promptsViewModel::updatePrompt,
    ) { it ->
        LaunchedEffect(Unit) {
            if (it != null) {
                onLoginSuccess(it)
            } else {
                promptsViewModel.updatePrompt(
                    PromptTypeShow.Error(
                        message = context.getString(R.string.we_are_unable_to_process_your_request),
                        onButtonClick = {},
                        onDismiss = { promptsViewModel.updatePrompt(null) }
                    )
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewWelcomeScreen() {
    WelcomeScreen {  }
}


