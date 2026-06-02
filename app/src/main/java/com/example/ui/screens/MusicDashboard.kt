package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ChimalaEvent
import com.example.data.MediaItem
import com.example.ui.theme.SunsetOrange
import com.example.ui.theme.AmberGold
import com.example.ui.theme.MalachiteTeal
import com.example.ui.theme.CardBg
import com.example.ui.theme.DarkBg
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.EventFilter
import com.example.ui.viewmodel.MediaFilter
import com.example.ui.viewmodel.MusicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicDashboard(viewModel: MusicViewModel) {
    val context = LocalContext.current
    val currentTab by viewModel.currentTab.collectAsState()
    val currentPlaying by viewModel.currentPlayingMedia.collectAsState()
    var isExpandedPlayer by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Chimala Music",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = (-0.5).sp
                            )
                            Box(
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                        RoundedCornerShape(6.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                              ) {
                                  Text(
                                      text = "Mbeya",
                                      fontSize = 9.sp,
                                      fontWeight = FontWeight.Bold,
                                      color = MaterialTheme.colorScheme.primary
                                  )
                              }
                        }
                        Text(
                            text = "MUZIKI NA MATUKIO",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                ),
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable {
                                Toast.makeText(context, "Muziki na Sanaa kutoka Chimala, Mbeya, Tanzania! 🇹🇿", Toast.LENGTH_LONG).show()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Taarifa",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                },
                modifier = Modifier.testTag("top_bar")
            )
        },
        bottomBar = {
            CustomBottomBar(
                currentTab = currentTab,
                onTabSelected = { viewModel.selectTab(it) }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Main views switcher
            when (currentTab) {
                AppTab.EXPLORE -> ExploreScreen(viewModel = viewModel)
                AppTab.EVENTS -> EventsScreen(viewModel = viewModel)
                AppTab.UPLOAD -> UploadScreen(viewModel = viewModel)
            }

            // Slide up mini-player
            AnimatedVisibility(
                visible = currentPlaying != null && !isExpandedPlayer,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
            ) {
                MiniPlayer(
                    mediaItem = currentPlaying!!,
                    viewModel = viewModel,
                    onClick = { isExpandedPlayer = true }
                )
            }

            // Expanded full screen player
            AnimatedVisibility(
                visible = isExpandedPlayer && currentPlaying != null,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                modifier = Modifier.fillMaxSize()
            ) {
                ExpandedPlayer(
                    mediaItem = currentPlaying ?: return@AnimatedVisibility,
                    viewModel = viewModel,
                    onDismiss = { isExpandedPlayer = false }
                )
            }
        }
    }
}

// Bottom Navigation Bar
@Composable
fun CustomBottomBar(
    currentTab: AppTab,
    onTabSelected: (AppTab) -> Unit
) {
    val outlineColor = MaterialTheme.colorScheme.outline
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        modifier = Modifier
            .testTag("bottom_nav")
            .drawBehind {
                drawLine(
                    color = outlineColor.copy(alpha = 0.4f),
                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            }
    ) {
        NavigationBarItem(
            selected = currentTab == AppTab.EXPLORE,
            onClick = { onTabSelected(AppTab.EXPLORE) },
            icon = {
                Text(
                    text = "🎵",
                    fontSize = 20.sp
                )
            },
            label = { Text("Nyimbo & Video", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                selectedTextColor = MaterialTheme.colorScheme.onSurface,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                indicatorColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.testTag("tab_explore")
        )

        NavigationBarItem(
            selected = currentTab == AppTab.EVENTS,
            onClick = { onTabSelected(AppTab.EVENTS) },
            icon = {
                Text(
                    text = "📅",
                    fontSize = 20.sp
                )
            },
            label = { Text("Matukio", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                selectedTextColor = MaterialTheme.colorScheme.onSurface,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                indicatorColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.testTag("tab_events")
        )

        NavigationBarItem(
            selected = currentTab == AppTab.UPLOAD,
            onClick = { onTabSelected(AppTab.UPLOAD) },
            icon = {
                Text(
                    text = "📤",
                    fontSize = 20.sp
                )
            },
            label = { Text("Weka Sanaa", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                selectedTextColor = MaterialTheme.colorScheme.onSurface,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                indicatorColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.testTag("tab_upload")
        )
    }
}

// Featured News Promotion banner 16:9 layout
@Composable
fun FeaturedNewsBanner(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("featured_news_banner"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.outline,
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                )
            )
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            // Dynamic decoration background inside the box
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                                MaterialTheme.colorScheme.surfaceVariant
                            )
                        )
                    )
            )

            // Glowing circles to represent art
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = SunsetOrange.copy(alpha = 0.12f),
                    radius = size.width / 4,
                    center = androidx.compose.ui.geometry.Offset(size.width * 0.8f, size.height * 0.2f)
                )
            }

            // Bottom-up dark gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.75f)
                            ),
                            startY = 100f
                        )
                    )
            )

            // Content Left-Bottom
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                // Badge
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "HABARI MOTO",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Tamasha la Chimala 2026: Tiketi Zaanza Kuuzwa!",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Ungana na wasanii wakubwa wa Chimala uwanjani.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.9f),
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// Horizontal Video Item Renderer
@Composable
fun HorizontalVideoItem(
    item: MediaItem,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .clickable(onClick = onClick)
            .padding(4.dp)
            .testTag("hot_video_${item.id}"),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Video thumbnail box representing 16:9 aspect ratio
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        )
                    )
                )
        ) {
            // Play icon
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Cheza",
                tint = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(28.dp)
            )

            // Duration badge at the bottom-right
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(6.dp)
                    .background(Color.Black.copy(alpha = 0.65f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    text = item.duration,
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Text titles
        Column(modifier = Modifier.padding(horizontal = 2.dp)) {
            Text(
                text = item.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = item.artist,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// Subscreen 1: Nyimbo & Video Explore Feed
@Composable
fun ExploreScreen(viewModel: MusicViewModel) {
    val searchVal by viewModel.searchQuery.collectAsState()
    val mediaFilter by viewModel.mediaFilter.collectAsState()
    val itemsList by viewModel.filteredMedia.collectAsState()
    val songs = remember(itemsList) { itemsList.filter { it.type == "SONG" } }
    val videos = remember(itemsList) { itemsList.filter { it.type == "VIDEO" } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Search bar
        OutlinedTextField(
            value = searchVal,
            onValueChange = { viewModel.updateSearchQuery(it) },
            placeholder = { Text("Tafuta nyimbo, wasanii, au mitindo...", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Tafuta",
                    tint = SunsetOrange
                )
            },
            trailingIcon = {
                if (searchVal.isNotEmpty()) {
                    IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Futa")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SunsetOrange,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.15f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .testTag("search_field")
        )

        // Filters Group
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            MediaFilterChip(
                label = "Zote",
                selected = mediaFilter == MediaFilter.ALL,
                onClick = { viewModel.setMediaFilter(MediaFilter.ALL) }
            )
            MediaFilterChip(
                label = "🎵 Nyimbo za Sauti",
                selected = mediaFilter == MediaFilter.SONGS,
                onClick = { viewModel.setMediaFilter(MediaFilter.SONGS) }
            )
            MediaFilterChip(
                label = "🎥 Video za Muziki",
                selected = mediaFilter == MediaFilter.VIDEOS,
                onClick = { viewModel.setMediaFilter(MediaFilter.VIDEOS) }
            )
        }

        // List body
        if (itemsList.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "🎵",
                        fontSize = 48.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Hakuna habari au wimbo uliopatikana",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Tafadhali badilisha utafutaji wako au weka wimbo mpya katika kichupo cha 'Weka Sanaa'.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .testTag("media_list")
            ) {
                if (searchVal.isEmpty() && mediaFilter == MediaFilter.ALL) {
                    // 1. Featured Headline Promotion
                    item {
                        FeaturedNewsBanner(onClick = {
                            viewModel.selectTab(AppTab.EVENTS)
                        })
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // 2. Horizontal Video segment
                    if (videos.isNotEmpty()) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Video Mpya za Chimala",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Zote",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .clickable { viewModel.setMediaFilter(MediaFilter.VIDEOS) }
                                        .padding(4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(videos, key = { "horiz_${it.id}" }) { item ->
                                    HorizontalVideoItem(
                                        item = item,
                                        onClick = { viewModel.selectMedia(item) }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }

                    // 3. Vertical tracks list
                    if (songs.isNotEmpty()) {
                        item {
                            FeaturedSectionHeader("Nyimbo za Hivi Punde")
                        }

                        items(songs, key = { "song_${it.id}" }) { item ->
                            MediaCard(
                                item = item,
                                onClick = { viewModel.selectMedia(item) },
                                onLike = { viewModel.likeMediaItem(item) },
                                onDelete = { viewModel.deleteMediaItem(item) }
                            )
                        }
                    }
                } else {
                    // Regular list view when filtered/searching
                    item {
                        FeaturedSectionHeader(
                            if (mediaFilter == MediaFilter.SONGS) "Nyimbo za Sauti tu"
                            else if (mediaFilter == MediaFilter.VIDEOS) "Video za Muziki tu"
                            else "Matokeo ya Utafutaji"
                        )
                    }

                    items(itemsList, key = { it.id }) { item ->
                        MediaCard(
                            item = item,
                            onClick = { viewModel.selectMedia(item) },
                            onLike = { viewModel.likeMediaItem(item) },
                            onDelete = { viewModel.deleteMediaItem(item) }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp)) // clearance for mini-player
                }
            }
        }
    }
}

@Composable
fun MediaFilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
    AssistChip(
        onClick = onClick,
        label = { Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (selected) SunsetOrange else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
            labelColor = if (selected) Color.White else MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.testTag("chip_$label")
    )
}

@Composable
fun FeaturedSectionHeader(title: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            color = AmberGold,
            letterSpacing = 0.8.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(3.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(SunsetOrange, MalachiteTeal)
                    ),
                    shape = RoundedCornerShape(2.dp)
                )
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

// Media Card Renderer
@Composable
fun MediaCard(
    item: MediaItem,
    onClick: () -> Unit,
    onLike: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("media_card_${item.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                    SunsetOrange.copy(alpha = 0.1f)
                )
            )
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Album art representation
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = if (item.type == "SONG") {
                                listOf(SunsetOrange.copy(alpha = 0.8f), AmberGold.copy(alpha = 0.8f))
                            } else {
                                listOf(SunsetOrange.copy(alpha = 0.8f), Color(0xFF9013FE))
                            }
                        )
                    )
            ) {
                // Procedural beautiful design for media type
                Text(
                    text = if (item.type == "SONG") "🎵" else "🎥",
                    fontSize = 24.sp
                )

                // Small media type badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(
                            color = if (item.type == "SONG") MalachiteTeal else SunsetOrange,
                            shape = RoundedCornerShape(topStart = 6.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                ) {
                    Text(
                        text = if (item.type == "SONG") "Mp3" else "Mp4",
                        color = Color.White,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Body text
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.artist,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                                RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 5.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = item.category,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = SunsetOrange
                        )
                    }

                    Text(
                        text = "⏱️ ${item.duration}",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )

                    Text(
                        text = "👁️ ${item.views}",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.width(4.dp))

            // Reactions Buttons
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(
                    onClick = { onLike() },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Penda",
                        tint = if (item.likes > 100) SunsetOrange else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = "${item.likes}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .size(32.dp)
                    .testTag("delete_media_${item.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Futa",
                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}


// Subscreen 2: Matukio ya Chimala & Habari
@Composable
fun EventsScreen(viewModel: MusicViewModel) {
    val searchVal by viewModel.searchQuery.collectAsState()
    val eventFilter by viewModel.eventFilter.collectAsState()
    val eventsList by viewModel.filteredEvents.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Search bar (reuses VM query)
        OutlinedTextField(
            value = searchVal,
            onValueChange = { viewModel.updateSearchQuery(it) },
            placeholder = { Text("Tafuta matasha, habari, au maeneo...", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Tafuta",
                    tint = SunsetOrange
                )
            },
            trailingIcon = {
                if (searchVal.isNotEmpty()) {
                    IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Futa")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SunsetOrange,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.15f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )

        // Events filter chips
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            MediaFilterChip(
                label = "Zote",
                selected = eventFilter == EventFilter.ALL,
                onClick = { viewModel.setEventFilter(EventFilter.ALL) }
            )
            MediaFilterChip(
                label = "📅 Matamasha & Matukio",
                selected = eventFilter == EventFilter.CONCERTS,
                onClick = { viewModel.setEventFilter(EventFilter.CONCERTS) }
            )
            MediaFilterChip(
                label = "📰 Habari za Hivi Punde",
                selected = eventFilter == EventFilter.NEWS,
                onClick = { viewModel.setEventFilter(EventFilter.NEWS) }
            )
        }

        // List View
        if (eventsList.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "📅",
                        fontSize = 48.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Hakuna matukio au habari",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Tanzania ni nchi adhimu! Hakuna habari ya matamasha inayolingana na utafutaji wako.",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .testTag("events_list")
            ) {
                item {
                    FeaturedSectionHeader("Ratiba ya Matukio & Habari za Matamasha")
                }

                items(eventsList, key = { it.id }) { item ->
                    EventCard(
                        event = item,
                        onLike = { viewModel.likeEventItem(item) },
                        onDelete = { viewModel.deleteEventItem(item) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp)) // player clearance
                }
            }
        }
    }
}

@Composable
fun EventCard(
    event: ChimalaEvent,
    onLike: () -> Unit,
    onDelete: () -> Unit
) {
    val isNews = event.type == "NEWS"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("event_card_${event.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isNews) {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
            }
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                    if (isNews) MalachiteTeal.copy(alpha = 0.1f) else SunsetOrange.copy(alpha = 0.1f)
                )
            )
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Label Row
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = if (isNews) MalachiteTeal.copy(alpha = 0.15f) else SunsetOrange.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (isNews) "HABARI ZA HIVI PUNDE" else "TAMASHA NA MATUKIO",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isNews) MalachiteTeal else SunsetOrange,
                        letterSpacing = 0.5.sp
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Futa",
                        tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Event/News Title
            Text(
                text = event.title,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Body Description
            Text(
                text = event.description,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Metadata items
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "📅 ${event.date}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = AmberGold
                )

                if (event.location.isNotBlank()) {
                    Text(
                        text = "📍 ${event.location}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Interactive Bottom Panel
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Price Tag (If Concert)
                if (event.price.isNotBlank()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Kiingilio: ",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                        Text(
                            text = event.price,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (event.price.lowercase() == "bure") MalachiteTeal else AmberGold
                        )
                    }
                } else {
                    Text(
                        text = "Muandaaji: ${event.organizer}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Likes count
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onLike() }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Penda",
                        tint = SunsetOrange,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${event.likes}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}


// Subscreen 3: Add/Post Content
@Composable
fun UploadScreen(viewModel: MusicViewModel) {
    val context = LocalContext.current
    var activeSubTab by remember { mutableStateOf(0) } // 0: Wimbo/Video, 1: Tamasha/Habari

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        FeaturedSectionHeader("Weka na Shiriki Kazi za Wasanii Chimala")

        Text(
            text = "Hapa unaweza kuweka nyimbo au video mpya za wasanii, au utaka kuratibu tamasha au kutoa habari za hivi punde kuhusu muziki wa Chimala Mbarali.",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Custom tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    RoundedCornerShape(8.dp)
                )
                .padding(4.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (activeSubTab == 0) SunsetOrange else Color.Transparent)
                    .clickable { activeSubTab = 0 }
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = "Nyimbo / Video",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = if (activeSubTab == 0) Color.White else MaterialTheme.colorScheme.onSurface
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (activeSubTab == 1) SunsetOrange else Color.Transparent)
                    .clickable { activeSubTab = 1 }
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = "Tamasha / Habari",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = if (activeSubTab == 1) Color.White else MaterialTheme.colorScheme.onSurface
                )
            }
        }

        if (activeSubTab == 0) {
            // Media Form State
            var mediaTitle by remember { mutableStateOf("") }
            var mediaArtist by remember { mutableStateOf("") }
            var mediaType by remember { mutableStateOf("SONG") } // SONG or VIDEO
            var mediaCategory by remember { mutableStateOf("Bongo Flava") }
            var mediaDuration by remember { mutableStateOf("3:30") }
            var mediaLyrics by remember { mutableStateOf("") }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = mediaTitle,
                    onValueChange = { mediaTitle = it },
                    label = { Text("Kichwa cha Wimbo/Video") },
                    placeholder = { Text("Mf. Chimala Yetu") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("add_media_title"),
                    colors = formFieldColor()
                )

                OutlinedTextField(
                    value = mediaArtist,
                    onValueChange = { mediaArtist = it },
                    label = { Text("Msanii au Bendi") },
                    placeholder = { Text("Mf. Mfalme wa Singeli") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("add_media_artist"),
                    colors = formFieldColor()
                )

                // Selector for type SONG or VIDEO
                Column {
                    Text(
                        text = "Aina ya Sanaa",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SunsetOrange
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        OutlinedButton(
                            onClick = { mediaType = "SONG" },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (mediaType == "SONG") SunsetOrange else Color.Transparent,
                                contentColor = if (mediaType == "SONG") Color.White else MaterialTheme.colorScheme.onSurface
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f).testTag("choose_song")
                        ) {
                            Text("🎧 Audio (Mp3)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = { mediaType = "VIDEO" },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (mediaType == "VIDEO") SunsetOrange else Color.Transparent,
                                contentColor = if (mediaType == "VIDEO") Color.White else MaterialTheme.colorScheme.onSurface
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f).testTag("choose_video")
                        ) {
                            Text("🎥 Video (Mp4)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = mediaCategory,
                        onValueChange = { mediaCategory = it },
                        label = { Text("Mtindo") },
                        placeholder = { Text("Singeli, Gospel...") },
                        singleLine = true,
                        modifier = Modifier.weight(1f).testTag("add_media_category"),
                        colors = formFieldColor()
                    )

                    OutlinedTextField(
                        value = mediaDuration,
                        onValueChange = { mediaDuration = it },
                        label = { Text("Muda (Dakika)") },
                        placeholder = { Text("Mf. 3:45") },
                        singleLine = true,
                        modifier = Modifier.weight(1f).testTag("add_media_duration"),
                        colors = formFieldColor()
                    )
                }

                OutlinedTextField(
                    value = mediaLyrics,
                    onValueChange = { mediaLyrics = it },
                    label = { Text(if (mediaType == "SONG") "Mashairi ya Wimbo (Lyrics)" else "Maelezo mafupi ya Video") },
                    placeholder = { Text("Weka maneno au maelezo hapa ili wasikilizaji waimbe pamoja nawe...") },
                    maxLines = 8,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .testTag("add_media_lyrics"),
                    colors = formFieldColor()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (mediaTitle.isBlank()) {
                            Toast.makeText(context, "Kichwa cha wimbo ni lazima kuwekwa!", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.insertUploadedMedia(
                                title = mediaTitle,
                                artist = mediaArtist,
                                type = mediaType,
                                category = mediaCategory,
                                duration = mediaDuration,
                                lyrics = mediaLyrics
                            )
                            Toast.makeText(context, "Sanaa mpya imewekwa kikamilifu Chimala!", Toast.LENGTH_LONG).show()
                            // Clear form
                            mediaTitle = ""
                            mediaArtist = ""
                            mediaLyrics = ""
                            // Switch tab to explore feed
                            viewModel.selectTab(AppTab.EXPLORE)
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SunsetOrange,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("submit_media_btn")
                ) {
                    Text("Weka Sanaa Sasa Chimala 🚀", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            // Event Form State
            var eventTitle by remember { mutableStateOf("") }
            var eventDesc by remember { mutableStateOf("") }
            var eventDate by remember { mutableStateOf("") }
            var eventLocation by remember { mutableStateOf("") }
            var eventPrice by remember { mutableStateOf("") }
            var eventType by remember { mutableStateOf("EVENT") } // EVENT or NEWS
            var eventOrganizer by remember { mutableStateOf("") }

            Column(
                verticalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                OutlinedTextField(
                    value = eventTitle,
                    onValueChange = { eventTitle = it },
                    label = { Text("Kichwa cha Tukio au Habari") },
                    placeholder = { Text("Mf. Tamasha la Sanaa Chimala") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("add_event_title"),
                    colors = formFieldColor()
                )

                OutlinedTextField(
                    value = eventDesc,
                    onValueChange = { eventDesc = it },
                    label = { Text("Maelezo ya Kina") },
                    placeholder = { Text("Weka maelezo, faida, ratiba, na namna ya kushabiki...") },
                    maxLines = 6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .testTag("add_event_desc"),
                    colors = formFieldColor()
                )

                // Select event type EVENT or NEWS
                Column {
                    Text(
                        text = "Aina ya Post",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SunsetOrange
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        OutlinedButton(
                            onClick = { eventType = "EVENT" },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (eventType == "EVENT") SunsetOrange else Color.Transparent,
                                contentColor = if (eventType == "EVENT") Color.White else MaterialTheme.colorScheme.onSurface
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f).testTag("choose_event")
                        ) {
                            Text("📅 Tamasha/Tukio", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = { eventType = "NEWS" },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (eventType == "NEWS") SunsetOrange else Color.Transparent,
                                contentColor = if (eventType == "NEWS") Color.White else MaterialTheme.colorScheme.onSurface
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f).testTag("choose_news")
                        ) {
                            Text("📰 Habari ya Muziki", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = eventDate,
                        onValueChange = { eventDate = it },
                        label = { Text("Tarehe") },
                        placeholder = { Text("Mf. Julai 15, 2026") },
                        singleLine = true,
                        modifier = Modifier.weight(1f).testTag("add_event_date"),
                        colors = formFieldColor()
                    )

                    OutlinedTextField(
                        value = eventLocation,
                        onValueChange = { eventLocation = it },
                        label = { Text("Ukumbi/Mahali") },
                        placeholder = { Text("Mf. Uwanja wa Chimala") },
                        singleLine = true,
                        modifier = Modifier.weight(1f).testTag("add_event_location"),
                        colors = formFieldColor()
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = eventPrice,
                        onValueChange = { eventPrice = it },
                        label = { Text("Kiingilio (Price)") },
                        placeholder = { Text("Bure au Tsh 5000") },
                        singleLine = true,
                        modifier = Modifier.weight(1f).testTag("add_event_price"),
                        colors = formFieldColor()
                    )

                    OutlinedTextField(
                        value = eventOrganizer,
                        onValueChange = { eventOrganizer = it },
                        label = { Text("Mratibu (Organizer)") },
                        placeholder = { Text("Kikundi cha Wasanii...") },
                        singleLine = true,
                        modifier = Modifier.weight(1f).testTag("add_event_organizer"),
                        colors = formFieldColor()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (eventTitle.isBlank() || eventDesc.isBlank()) {
                            Toast.makeText(context, "Kichwa na Maelezo ni lazima kupostiwa!", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.insertUploadedEvent(
                                title = eventTitle,
                                description = eventDesc,
                                date = eventDate,
                                location = eventLocation,
                                price = eventPrice,
                                type = eventType,
                                organizer = eventOrganizer
                            )
                            Toast.makeText(context, "Ujumbe/Tamasha limewekwa Chimala!", Toast.LENGTH_LONG).show()
                            // Clear form
                            eventTitle = ""
                            eventDesc = ""
                            eventDate = ""
                            eventLocation = ""
                            eventPrice = ""
                            eventOrganizer = ""
                            // Switch tab to events list
                            viewModel.selectTab(AppTab.EVENTS)
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MalachiteTeal,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("submit_event_btn")
                ) {
                    Text("Pachika Ratiba au Tukio Chimala 📢", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun formFieldColor() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = SunsetOrange,
    focusedLabelColor = SunsetOrange,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
    unfocusedContainerColor = Color.Transparent
)


// MINI AUDIO & VIDEO PLAYER
@Composable
fun MiniPlayer(
    mediaItem: MediaItem,
    viewModel: MusicViewModel,
    onClick: () -> Unit
) {
    val isPlaying by viewModel.isPlaying.collectAsState()
    val progress by viewModel.playbackProgress.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable(onClick = onClick)
            .testTag("mini_player"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Live thin progress bar at top of card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .background(SunsetOrange)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Small thumbnail circle
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(SunsetOrange, AmberGold)
                                )
                            )
                    ) {
                        Text(
                            text = if (mediaItem.type == "SONG") "🎵" else "🎥",
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = mediaItem.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.testTag("mini_player_title"),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${mediaItem.artist} • ${mediaItem.category}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Controls
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { viewModel.togglePlayPause() },
                        modifier = Modifier
                            .size(36.dp)
                            .background(SunsetOrange, CircleShape)
                            .testTag("mini_play_pause")
                    ) {
                        if (isPlaying) {
                            // Custom Pause icon drawing
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(2.dp),
                                modifier = Modifier.align(Alignment.CenterVertically)
                            ) {
                                Box(modifier = Modifier.width(3.dp).height(12.dp).background(Color.White, RoundedCornerShape(0.5.dp)))
                                Box(modifier = Modifier.width(3.dp).height(12.dp).background(Color.White, RoundedCornerShape(0.5.dp)))
                            }
                        } else {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    IconButton(
                        onClick = { viewModel.likeMediaItem(mediaItem) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Penda",
                            tint = SunsetOrange,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}


// EXPANDED SINGLE FULL-SCREEN PLAYER
@Composable
fun ExpandedPlayer(
    mediaItem: MediaItem,
    viewModel: MusicViewModel,
    onDismiss: () -> Unit
) {
    val isPlaying by viewModel.isPlaying.collectAsState()
    val progress by viewModel.playbackProgress.collectAsState()
    val seconds by viewModel.playbackSeconds.collectAsState()
    val isMuted by viewModel.isMuted.collectAsState()

    // Bouncing music bar scaling for infinite transition (Equalizer Simulation)
    val infiniteTransition = rememberInfiniteTransition(label = "bouncing_bars")
    val animScale1 by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bar1"
    )
    val animScale2 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(300),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bar2"
    )
    val animScale3 by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bar3"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        CardBg.copy(alpha = 0.95f),
                        DarkBg
                    )
                )
            )
            .testTag("expanded_player")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Dismiss arrow
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(44.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), CircleShape)
                        .testTag("back_to_dashboard")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Rudi nyuma",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .background(
                            color = if (mediaItem.type == "SONG") SunsetOrange.copy(alpha = 0.2f) else MalachiteTeal.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (mediaItem.type == "SONG") "IKICHEZA SAUTI (Mp3)" else "IKICHEZA VIDEO (Mp4)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (mediaItem.type == "SONG") SunsetOrange else MalachiteTeal,
                        letterSpacing = 0.8.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Cinematic Media / Video viewframe visualization
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(
                        brush = Brush.radialGradient(
                            colors = if (mediaItem.type == "SONG") {
                                listOf(CardBg, DarkBg)
                            } else {
                                listOf(Color(0xFF2E0854), DarkBg)
                            }
                        )
                    )
            ) {
                // If it is a video, draw a nice mock streaming visualizer loop overlay, otherwise standard vinyl circle
                if (mediaItem.type == "VIDEO") {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeW = 2.dp.toPx()
                        for (i in 1..4) {
                            drawCircle(
                                color = SunsetOrange.copy(alpha = 0.08f * i),
                                radius = (40 * i + (progress * 50)).dp.toPx(),
                                style = androidx.compose.ui.graphics.drawscope.Stroke(
                                    width = strokeW,
                                    cap = StrokeCap.Round
                                )
                            )
                        }
                    }

                    // Rotating star / video placeholder panel
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "🎥",
                            fontSize = 48.sp
                        )
                        Text(
                            text = "🎥 Kichocheo cha Video ya Wasanii",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SunsetOrange
                        )
                        // Bouncing equalizer bars to represent video streaming levels
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.height(24.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(4.dp)
                                    .fillMaxHeight(if (isPlaying) animScale1 else 0.3f)
                                    .background(MalachiteTeal, RoundedCornerShape(2.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .width(4.dp)
                                    .fillMaxHeight(if (isPlaying) animScale2 else 0.5f)
                                    .background(MalachiteTeal, RoundedCornerShape(2.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .width(4.dp)
                                    .fillMaxHeight(if (isPlaying) animScale3 else 0.2f)
                                    .background(MalachiteTeal, RoundedCornerShape(2.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .width(4.dp)
                                    .fillMaxHeight(if (isPlaying) animScale1 else 0.4f)
                                    .background(MalachiteTeal, RoundedCornerShape(2.dp))
                            )
                        }
                    }
                } else {
                    // Standard premium vinyl disc art
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF13111A))
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawCircle(
                                color = Color.DarkGray.copy(alpha = 0.3f),
                                radius = size.minDimension / 2.2f,
                                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
                            )
                            drawCircle(
                                color = Color.DarkGray.copy(alpha = 0.2f),
                                radius = size.minDimension / 3f,
                                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
                            )
                        }

                        // Center sticker with custom initials
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(SunsetOrange, AmberGold)
                                    )
                                )
                        ) {
                            Text(
                                text = mediaItem.artist.take(2).uppercase(),
                                fontWeight = FontWeight.Black,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Metadata: Title and Artist
            Text(
                text = mediaItem.title,
                fontWeight = FontWeight.Black,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.testTag("expanded_player_title")
            )
            Text(
                text = "Msanii: ${mediaItem.artist} • ${mediaItem.category}",
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                color = SunsetOrange,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Timeline slider
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Slider(
                    value = progress,
                    onValueChange = { viewModel.seekTo(it) },
                    colors = SliderDefaults.colors(
                        thumbColor = SunsetOrange,
                        activeTrackColor = SunsetOrange,
                        inactiveTrackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = viewModel.formatSeconds(seconds),
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                    Text(
                        text = mediaItem.duration,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Player Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Like action
                IconButton(
                    onClick = { viewModel.likeMediaItem(mediaItem) },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Penda",
                        tint = SunsetOrange,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Play / Pause Circle Accent Button
                IconButton(
                    onClick = { viewModel.togglePlayPause() },
                    modifier = Modifier
                        .size(72.dp)
                        .background(SunsetOrange, CircleShape)
                        .testTag("expanded_play_pause")
                ) {
                    if (isPlaying) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Box(modifier = Modifier.width(5.dp).height(20.dp).background(Color.White, RoundedCornerShape(1.dp)))
                            Box(modifier = Modifier.width(5.dp).height(20.dp).background(Color.White, RoundedCornerShape(1.dp)))
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Cheza",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                // Volume toggle Mute/Unmute
                IconButton(
                    onClick = { viewModel.toggleMute() },
                    modifier = Modifier.size(48.dp)
                ) {
                    Text(
                        text = if (isMuted) "🔇" else "🔊",
                        fontSize = 24.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Scrollable Swahili lyrics or description text context cards
            if (mediaItem.lyrics.isNotBlank()) {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (mediaItem.type == "SONG") "🎤 Mashairi ya Wimbo" else "📝 Taarifa za Video",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SunsetOrange,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = mediaItem.lyrics,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            lineHeight = 22.sp,
                            modifier = Modifier.testTag("lyrics_box")
                        )
                    }
                }
            }
        }
    }
}
