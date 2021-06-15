package xyz.sachil.essence.util

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.core.MarkwonTheme
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.glide.GlideImagesPlugin
import xyz.sachil.essence.R
import xyz.sachil.essence.model.cache.CacheDatabase
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.widget.decoration.GridItemDecoration
import xyz.sachil.essence.widget.decoration.ListItemDecoration
import xyz.sachil.essence.widget.decoration.StaggeredGridItemDecoration
import java.io.File
import java.math.BigDecimal
import java.util.*

fun TypeData.hasImage(): Boolean {
    var prefix = ""
    var hasImage = false
    this.images.forEach {
        if (!it.isNullOrEmpty()) {
            prefix = it.toLowerCase(Locale.getDefault())
            if (prefix.startsWith("http") || prefix.startsWith("https")) {
                hasImage = true
                return@forEach
            }
        }
    }
    return hasImage
}

fun RecyclerView.addListItemDecoration(
    @ColorRes decorationColor: Int,
    decorationSize: Int = R.dimen.dp_8,
    containFirstItemTop: Boolean = false,
    containLastItemBottom: Boolean = false,
) {
    val decoration = ColorDrawable(context.resources.getColor(decorationColor))
    val size = context.resources.getDimensionPixelSize(decorationSize)
    addItemDecoration(
        ListItemDecoration(
            decoration,
            size,
            containFirstItemTop,
            containLastItemBottom
        )
    )
}

fun RecyclerView.addGridItemDecoration(
    @ColorRes decorationColor: Int,
    @DimenRes decorationSize: Int = R.dimen.dp_8
) {
    val decoration = ColorDrawable(context.resources.getColor(decorationColor))
    val size = context.resources.getDimensionPixelSize(decorationSize)
    addItemDecoration(GridItemDecoration(decoration, size, decoration, size))
}

fun RecyclerView.addStaggeredGridItemDecoration(
    @ColorRes decorationColor: Int,
    @DimenRes decorationSize: Int = R.dimen.dp_8
) {
    val decoration = ColorDrawable(context.resources.getColor(decorationColor))
    val size = context.resources.getDimensionPixelSize(decorationSize)
    addItemDecoration(StaggeredGridItemDecoration(decoration, size, decoration, size))
}

fun Context.createMarkdownHelper(): Markwon {
    //设置主题
    val themePlugin = object : AbstractMarkwonPlugin() {
        override fun configureTheme(builder: MarkwonTheme.Builder) {
            builder.headingBreakHeight(resources.getDimensionPixelSize(R.dimen.dp_0_5))
                .headingTextSizeMultipliers(floatArrayOf(1.3F, 1.2F, 1.1F, 1.0F, 0.83F, 0.67F))
                .headingBreakColor(resources.getColor(R.color.primaryColor))
                .codeTextSize(resources.getDimensionPixelSize(R.dimen.sp_12))
                .codeTextColor(resources.getColor(R.color.mediumEmphasisTextColor))
                .codeBlockTextSize(resources.getDimensionPixelSize(R.dimen.sp_12))
                .codeBlockTextColor(resources.getColor(R.color.mediumEmphasisTextColor))
                .codeBlockMargin(resources.getDimensionPixelSize(R.dimen.dp_4))
                .codeBackgroundColor(resources.getColor(R.color.codeBlockBackgroundColor))
                .blockMargin(resources.getDimensionPixelSize(R.dimen.dp_2))
        }
    }

    val markdownBuilder = Markwon.builder(this)
        //用于显示table
        .usePlugin(TablePlugin.create(this))
        //用于显示图片
        .usePlugin(GlideImagesPlugin.create(this))
        //用于显示网页
        .usePlugin(HtmlPlugin.create())
        .usePlugin(themePlugin)

    return markdownBuilder.build()
}

fun Context.getWeeklyPopularType(): Utils.PopularType {
    val sp = getSharedPreferences(Utils.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    return when (sp.getString("weeklyPopularType", Utils.PopularType.VIEWS.type)) {
        Utils.PopularType.VIEWS.type -> Utils.PopularType.VIEWS
        else -> Utils.PopularType.LIKES
    }
}

fun Context.setWeeklyPopularType(popularType: Utils.PopularType) {
    val sp = getSharedPreferences(Utils.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    val editor = sp.edit()
    editor.putString("weeklyPopularType", popularType.type)
    editor.apply()
}

fun Context.getCacheSize(): String = cacheDir.getSize().getFormatSize()

fun Context.clearCache() {
    cacheDir.clear()
    clearDatabase()
}

fun Context.clearDatabase() {
    val database = CacheDatabase.newInstance(this)
    database.typeDao().deleteAllData()
    database.typeDataDao().deleteAllData()
}


fun File.clear(): Boolean {
    if (isDirectory) {
        listFiles().forEach {
            val succeed = it.clear()
            if (!succeed) {
                return false
            }
        }
    }
    return delete()
}


fun File.getSize(): Long {
    var size = 0L
    if (isDirectory) {
        listFiles().forEach {
            size += it.getSize()
        }
    } else {
        size = length()
    }
    return size
}

fun Long.getFormatSize(): String {
    val kiloByte = this / 1024F
    if (kiloByte < 1) {
        return "$this B"
    }

    val megaByte = kiloByte / 1024F
    if (megaByte < 1) {
        return BigDecimal("$kiloByte").setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " KB"
    }

    val gigaByte = megaByte / 1024F
    if (gigaByte < 1) {
        return BigDecimal("$megaByte").setScale(2, BigDecimal.ROUND_HALF_UP)
            .toPlainString() + " MB"
    }
    val teraByte = gigaByte / 1024F
    if (teraByte < 1) {
        return BigDecimal("$gigaByte").setScale(2, BigDecimal.ROUND_HALF_UP)
            .toPlainString() + " GB"
    }
    return BigDecimal("$teraByte").setScale(2, BigDecimal.ROUND_HALF_UP)
        .toPlainString() + " TB"
}
