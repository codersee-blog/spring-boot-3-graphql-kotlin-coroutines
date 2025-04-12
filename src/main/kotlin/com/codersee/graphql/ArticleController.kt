package com.codersee.graphql

import kotlinx.coroutines.flow.Flow
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class ArticleController(
    private val articleService: ArticleService,
) {

    @QueryMapping
    suspend fun article(@Argument id: String): Article? =
        articleService.findArticleById(id)

    @QueryMapping
    fun articles(): Flow<Article> =
        articleService.findAllArticles()

    @SchemaMapping
    suspend fun author(article: Article): User =
        articleService.findUserById(article.authorId)!!

    @SchemaMapping
    suspend fun author(comment: Comment): User =
        articleService.findUserById(comment.userId)!!

    @SchemaMapping
    fun comments(article: Article): Flow<Comment> =
        articleService.findCommentsByArticleId(article.id)

    @MutationMapping
    suspend fun createArticle(@Argument input: CreateArticleInput): Article =
        articleService.createArticle(input)

    @MutationMapping
    suspend fun addComment(
        @Argument id: String,
        @Argument input: AddCommentInput,
    ): Comment =
        articleService.addComment(id, input)
}