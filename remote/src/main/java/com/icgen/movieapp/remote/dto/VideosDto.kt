package com.icgen.movieapp.remote.dto

data class SnippetDto(
    val publishedAt: String,
    val title: String,
    val description: String,
    val thumbnails: ThumbnailsDto,
    val channelTitle: String,
    val resourceId: ResourceIdDto,
)

data class ResourceIdDto(
    val kind: String,
    val videoId: String,
)

data class ThumbnailsDto(
    val default: ThumbnailDto,
    val medium: ThumbnailDto,
    val high: ThumbnailDto,
    val standard: ThumbnailDto,
    val maxres: ThumbnailDto,
)

data class ThumbnailDto(
    val url: String,
    val width: Int,
    val height: Int,
)

data class RelatedPlaylistsDto(
    val likes: String,
    val uploads: String
)

data class ContentDetailsDto(
    val relatedPlaylists: RelatedPlaylistsDto
)

data class ItemDto(
    val id: String,
    val snippet: SnippetDto,
)

data class ChannelsItemDto(
    val id: String,
    val snippet: SnippetDto,
    val contentDetails:ContentDetailsDto
)

data class VideosDto(
    val nextPageToken: String,
    val items: List<ItemDto>
)

data class ChannelsDto(
    val items: List<ChannelsItemDto>
)



