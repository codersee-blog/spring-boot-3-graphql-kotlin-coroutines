package com.codersee.graphql

data class User(
    val id: String,
    val name: String,
)

data class Article(
    val id: String,
    val title: String,
    val content: String,
    val authorId: String,
    val createdAt: String,
)

data class Comment(
    val id: String,
    val content: String,
    val articleId: String,
    val userId: String,
)

data class CreateArticleInput(
    val title: String,
    val content: String,
    val userId: String,
)

data class AddCommentInput(
    val content: String,
    val userId: String,
)

data class GenericNotFound(val msg: String) : RuntimeException(msg)
