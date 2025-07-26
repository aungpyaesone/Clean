package com.apsone.clean

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.apsone.auth.presentation.intro.IntroScreenRoot
import com.apsone.auth.presentation.login.LoginScreenRoot
import com.apsone.auth.presentation.register.RegisterScreenRoot
import com.apsone.run.presentation.active_run.ActiveRunScreenRoot
import com.apsone.run.presentation.run_overview.RunOverViewScreenRot

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLogin: Boolean = false,
    ) {
    NavHost(
        navController = navController,
        startDestination = if(isLogin) "run" else "auth"
    ){
        authGraph(navController)
        runGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController){
    navigation(
        route = "auth",
        startDestination = "intro"
    ) {
        composable(route = "intro"){
            IntroScreenRoot(
                onSignInClick = { navController.navigate("login")  },
                onSignUpClick = { navController.navigate("register") }
            )
        }

        composable(route = "register"){
            RegisterScreenRoot(
                onSignInClick = { navController.navigate("login"){
                    popUpTo("register"){
                        inclusive = true
                        saveState = true
                    }
                    restoreState = true
                } },
                onSuccessfulRegistration = {navController.navigate("login")}
            )
        }

        composable (route = "login"){
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate("run") {
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = { navController.navigate("register"){
                    popUpTo("login"){
                        inclusive = true
                        saveState = true
                    }
                    restoreState = true
                } }
            )
        }

    }
}

private fun NavGraphBuilder.runGraph(navController: NavHostController){
    navigation(
        startDestination = "run_overview",
        route = "run"
    ) {
        composable("run_overview"){
            RunOverViewScreenRot(onStartRunClick = {
                navController.navigate("active_run")
            })
        }
        composable("active_run") {
            ActiveRunScreenRoot(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}