package com.apsone.core.presentation.designsystem.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apsone.core.presentation.designsystem.ArrowLeftIcon
import com.apsone.core.presentation.designsystem.CleanGreen
import com.apsone.core.presentation.designsystem.CleanTheme
import com.apsone.core.presentation.designsystem.CleanWhite
import com.apsone.core.presentation.designsystem.LogoIcon
import com.apsone.core.presentation.designsystem.Poppins
import com.apsone.core.presentation.designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CleanToolbar(
    showBackButton: Boolean,
    title: String,
    menusItem:List<DropDownItem> = listOf(),
    onMenuItemClick: (Int) -> Unit = {},
    onBackClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    startContent: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier) {

    var isDropDownOpen by rememberSaveable {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                startContent?.invoke()
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = Poppins
                )
            }
        },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick){
                    Icon(
                        imageVector = ArrowLeftIcon,
                        contentDescription = stringResource(R.string.go_back),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        actions = {
            if(menusItem.isNotEmpty()){
                Box{
                    DropdownMenu(expanded = isDropDownOpen, onDismissRequest = {
                        isDropDownOpen = false
                    }) {
                        menusItem.forEachIndexed { index, dropDownItem ->
                            Row(
                                modifier = Modifier.
                                    clickable{
                                        onMenuItemClick(index)
                                        isDropDownOpen = false
                                    }.fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(imageVector = dropDownItem.icon  ,contentDescription = dropDownItem.title)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = dropDownItem.title, color = CleanWhite)
                            }
                        }
                    }
                    IconButton(onClick = { isDropDownOpen = !isDropDownOpen }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.menu),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        )

}

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun CleanToolbarPreview() {
    CleanTheme {
        CleanToolbar(
            showBackButton = false,
            title = "Runique",
            startContent = {
                Icon(
                    imageVector = LogoIcon,
                    contentDescription = null,
                    tint = CleanGreen,
                    modifier = Modifier.size(35.dp)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            menusItem = listOf(
                DropDownItem(
                    icon = Icons.Default.MoreVert,
                    title = "Analytics"
                )
            )
        )
    }
}