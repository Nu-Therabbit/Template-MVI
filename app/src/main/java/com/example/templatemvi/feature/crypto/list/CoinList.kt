package com.example.templatemvi.feature.crypto.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templatemvi.ui.theme.TemplateMVITheme

@Composable
fun CoinListRoot(
    viewModel: CoinListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CoinListScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun CoinListScreen(
    modifier: Modifier = Modifier,
    state: CoinListState,
    onAction: (CoinListAction) -> Unit,
) {
    val context = LocalContext.current
    when (state) {
        CoinListState.Idle,
        CoinListState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is CoinListState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // ไอคอนแจ้งเตือน
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(Modifier.height(16.dp))

                // หัวข้อ Error
                Text(
                    text = "เกิดข้อผิดพลาด!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(Modifier.height(8.dp))

                // ข้อความรายละเอียด Error
                Text(
                    text = "${state.error}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(Modifier.height(24.dp))

                // ปุ่ม Retry: ส่ง Action กลับไปยัง ViewModel
                Button(onClick = {}) {
                    Text("ลองใหม่")
                }
            }
        }

        is CoinListState.Success -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.coins) { coinUi ->
                    CoinListItem(
                        modifier = Modifier.fillMaxWidth(),
                        coinUi = coinUi,
                        onClick = {
                            onAction(CoinListAction.OnCoinClick(coinUi))
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TemplateMVITheme {
        CoinListScreen(
            state = CoinListState.Idle,
            onAction = {}
        )
    }
}