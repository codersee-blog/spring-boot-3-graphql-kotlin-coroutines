package com.codersee.graphql

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
object ArticleService {

    private val articles = mutableListOf(
        Article(
            id = "article-id-1",
            title = "article-title-1",
            content = "article-content-1",
            authorId = "user-id-2",
            createdAt = LocalDateTime.now().toString()
        ),
        Article(
            id = "article-id-2",
            title = "article-title-2",
            content = "article-content-2",
            authorId = "user-id-2",
            createdAt = LocalDateTime.now().toString()
        ),
        Article(
            id = "article-id-3",
            title = "article-title-3",
            content = "article-content-3",
            authorId = "user-id-3",
            createdAt = LocalDateTime.now().toString()
        ),
    )

    private val users = mutableListOf(
        User(id = "user-id-1", name = "user-name-1"),
        User(id = "user-id-2", name = "user-name-2"),
        User(id = "user-id-3", name = "user-name-3"),
    )

    private val comments = mutableListOf(
        Comment(id = "comment-id-1", content = "comment-content-1", articleId = "article-id-1", userId = "user-id-3"),
        Comment(id = "comment-id-2", content = "comment-content-2", articleId = "article-id-2", userId = "user-id-3"),
        Comment(id = "comment-id-3", content = "comment-content-3", articleId = "article-id-2", userId = "user-id-2"),
        Comment(id = "comment-id-4", content = "comment-content-4", articleId = "article-id-3", userId = "user-id-3"),
    )

    suspend fun findArticleById(id: String): Article? {
        delay(100)
        return articles.firstOrNull { it.id == id }
    }

    fun findAllArticles(): Flow<Article> = articles.asFlow()

    suspend fun findUserById(id: String): User? {
        delay(100)
        return users.firstOrNull { it.id == id }
    }

    fun findCommentsByArticleId(id: String): Flow<Comment> =
        comments.filter { it.articleId == id }.asFlow()

    suspend fun createArticle(input: CreateArticleInput): Article {
        delay(100)

        return Article(
            id = UUID.randomUUID().toString(),
            title = input.title,
            content = input.content,
            authorId = input.userId,
            createdAt = LocalDateTime.now().toString(),
        ).also { articles.add(it) }
    }

    suspend fun addComment(id: String, input: AddCommentInput): Comment {
        delay(100)

        users.firstOrNull { it.id == input.userId }
            ?: throw GenericNotFound("User not found")

        return articles.firstOrNull { it.id == id }
            ?.let {
                Comment(
                    id = UUID.randomUUID().toString(),
                    articleId = id,
                    content = input.content,
                    userId = input.userId,
                ).also { comments.add(it) }
            }
            ?: throw GenericNotFound("Article not found")

    }
}