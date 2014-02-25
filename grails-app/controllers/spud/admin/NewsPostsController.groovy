package spud.admin

import  spud.core.*
import  spud.security.*
import  spud.blog.*
@SpudApp(name="News Posts", thumbnail="spud/admin/news_thumb.png", order="11", enabled="spud.blog.newsEnabled", defaultEnabled="false")
@SpudSecure(['NEWS'])
class NewsPostsController extends PostsController{
    static namespace = 'spud_admin'

    protected news() {
        return true
    }
}
