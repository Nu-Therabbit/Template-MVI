package com.example.templatemvi.feature.crypto

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.templatemvi.ObserveAsEvents
import com.example.templatemvi.feature.crypto.detail.CoinDetailScreen
import com.example.templatemvi.feature.crypto.list.CoinListAction
import com.example.templatemvi.feature.crypto.list.CoinListScreen
import com.example.templatemvi.feature.crypto.list.CoinListViewModel
import kotlinx.coroutines.launch

import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveCoinListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.events) { event ->

    }
    val coroutineScope = rememberCoroutineScope()
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoinListScreen(
                    state = state,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when (action) {
                            is CoinListAction.OnCoinClick -> {
                                coroutineScope.launch {
                                    navigator.navigateTo(
                                        pane = ListDetailPaneScaffoldRole.Detail
                                    )
                                }
                            }
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                CoinDetailScreen(state = state)
            }
        },
        modifier = modifier
    )
}