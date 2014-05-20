# Spud Grails Blog

The `spud-blog` grails plugin adds support for blog and news management to your website via the drop-in spud suite of modules.
Have a web app that needs that final touch with some news or even a blog? Simply add this plugin!

**Features:**

* Configurable Blog
* Configurable News (Basically a blog section without commenting)
* Comment Support ( Coming soon with spam Akismet protection support )
* Schedule future posts
* Categories and Category Management
* RSS 2.0 / Atom feed support and auto sitemap generation

## Installation

Add the `spud-blog` module to your grails app plugins

```groovy
plugins {
  compile ':spud-security:0.4.2' //Only needed if you dont have your own auth integrated with grails security-bridge
  compile ':spud-blog:0.4.0'
}
```

## Configuration

This plugin supports several configuration options for setting up your blog.


```groovy
spud {
	blog {
		blogEnabled = true
		newsEnabled = false
		blogMapping = '/blog'
		newsMapping = '/news'
		newsLayout = 'main' //Base Layout to use
		blogLayout = 'main' //Base Layout to use
		blogName = 'Spud Blog' //Used for rss and atom feed meta
		blogDescription = 'Spud blog Description' //Used for rss and atom feed meta
		newsName = 'Spud News' //Used for rss and atom feed meta
		newsDescription = 'Spud News Description' //Used for rss and atom feed meta
	}
}
```

The default values should get you started, but as you can see it's definitely customizable for your needs.

### Using an Alternative DataSource

Spud supports running on a different datasource than your primary. This can be done by adding the following config example:

```groovy
spud {
	core {
		//By default this uses the DEFAULT datasource
		datasource = 'spud' //Set datasource name here
	}
}
```

## Usage

Simply point your browser to the spud admin dashboard (i.e. `http://localhost:8080/myapp/spud/admin`),
and you should see a Blog and/or News module on your dashboard (depending on which is enabled via config).

For public viewing you can simply point your browser to the `http://localhost:8080/myapp/blog` or `http://localhost:8080/myapp/news` endpoint (default mappings).

### Customizing Styling

It is fairly simple to customize the styling of your blog posts. Firstly, you can change the default layout wrapper for your content as mentioned in the Configuration section of this readme.
There also exists gsp files in this plugins `views/blog/` and `views/news` folder.
They each contain a `index.gsp`, and `show.gsp` file for viewing your blog posts. Simply copy these files into your specific application and customize to your hearts content.

**NOTE:** Soon a GANT script will be added such that you can simply run a grails command to get these templates copied into your app.

### Available TagLibs

Spud Blog adds a few taglibs for your convenience that can also conveniently be used as handlebars helpers in the spud cms page renderings.
For example, lets say we want to show the 3 most recent news posts on our homepage. With grails gsp we can do this by simply using the `sp:news` taglib like so:

```gsp
...
<div class='news-section'>
  <sp:news limit="3">
    <div class='news-post'>
      <h3>${post.title}</h3>
      <div class='content'>
        <sp:truncateHtml length="250" value="${post.render}"/>
      </div>
    </div>
  </sp:news>
</div>
...
```

Notice the use of another taglib here (`sp:truncateHtml`). This omits the html from the content for quick summary sections. We can also do this in spud cms templates using handlebars syntax like so:

```handlebars
<div class='news-section'>
  {{#news limit="3"}}
    <div class='news-post'>
      <h3>{{post.title}}</h3>
      <div class='content'>
	    {{{truncateHtml length="250" value=post.render}}}
      </div>
    </div>
  {{/news}}
</div>
```

Pretty straight forward right? The same usage can also be used with the `sp:blog` taglib. You can also change the name of the context variable `post` by specifying the `var=` attribute.

## Additional Resources

* Write your blog posts with Markdown using [spud-markdown](http://github.com/spud-grails/spud-markdown)!
* Drop into a content management system with [spud-cms](http://github.com/spud-grails/spud-cms)!


## Things to Be Done

There are several feature adds that remain to be finished which include

* Spud Post Category Integration
* Commenting and Comment Management
* Caching
* Comment Spam Protection
* GANT Scripts for convenience
* Custom Fields
