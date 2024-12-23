package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.dokter.HomeScreen

import com.example.ucp2.ui.view.dokter.InsertDokter
import com.example.ucp2.ui.view.jadwal.HomeJWView
import com.example.ucp2.ui.view.jadwal.InsertJadwal
import com.example.ucp2.ui.view.jadwal.UpdateJadwalView
import com.example.ucp2.ui.viewmodel.UpdateJadwalViewModel


@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route
    ) {
        composable(
            route = DestinasiHome.route
        ) {
            HomeScreen(

                onDetailClick = { idDokter ->
                    navController.navigate("${DestinasiDetail.route}/$idDokter")
                },
                onAddDokter = {
                    navController.navigate(DestinasiInsert.route)
                },
                onDetailJW = {
                  navController.navigate(DestinasiHomeJW.route)
                },
                onAddJadwal = {
                    navController.navigate(DestinasiInsertJadwal.route)
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiInsert.route) {
            InsertDokter(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier =modifier

            )
        }
        composable(route = DestinasiInsertJadwal.route) {
            InsertJadwal(
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
                onBack = {
                    navController.popBackStack()
                },
                onDokter = {  },
                onJadwal = {

                }
            )
        }
        composable(route = DestinasiUpdateJadwal.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateJadwal.idJadwal) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateJadwalView(
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
                onBack = {
                    navController.popBackStack()
                },
            )
        }


        composable(route = DestinasiHomeJW.route) {
            HomeJWView(
                onBackClick = {
                    navController.popBackStack()
                },
                onSearchClick = {
                    // Tambahkan logika pencarian di sini jika diperlukan
                },
                onAddJadwal = {
                    navController.navigate(DestinasiInsertJadwal.route)
                },
                onEditJadwal = {
                    navController.navigate("${DestinasiUpdateJadwal.route}/$it")
                },
                modifier = modifier
            )
        }
    }
}
