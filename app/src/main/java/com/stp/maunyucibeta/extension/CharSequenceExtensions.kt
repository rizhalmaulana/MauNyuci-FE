package com.stp.maunyucibeta.extension

import android.graphics.Typeface
import android.os.Build
import android.text.*
import android.text.style.BackgroundColorSpan
import android.text.style.CharacterStyle
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.ScaleXSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.text.HtmlCompat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern

private val FORMAT_SEQUENCE =
    Pattern.compile("%([0-9]+\\$|<?)([^a-zA-z%]*)([[a-zA-Z%]&&[^tT]]|[tT][a-zA-Z])")
private const val CONCATENATE_FORMATTER = "%1\$s%2\$s"
operator fun CharSequence.plus(other: CharSequence): SpannableStringBuilder =
    CONCATENATE_FORMATTER.formatSpanned(this, other)

fun CharSequence.appendNewLine() = CONCATENATE_FORMATTER.formatSpanned(this, "\n")
fun CharSequence.appendSpace() = CONCATENATE_FORMATTER.formatSpanned(this, " ")
fun <T : CharacterStyle> CharSequence.applyStyles(vararg styles: T): CharSequence =
    this.applyTags(*styles)

fun CharSequence.bold(): CharSequence = applyStyles(StyleSpan(Typeface.BOLD))
fun CharSequence.italic(): CharSequence = applyStyles(StyleSpan(Typeface.ITALIC))
fun CharSequence.underline(): CharSequence = applyStyles(UnderlineSpan())
fun CharSequence.scale(relativeSize: Float): CharSequence =
    applyStyles(RelativeSizeSpan(relativeSize))

fun CharSequence.scaleX(relativeSize: Float): CharSequence = applyStyles(ScaleXSpan(relativeSize))
fun CharSequence.backgroundColor(@ColorInt color: Int): CharSequence =
    applyStyles(BackgroundColorSpan(color))

fun CharSequence.strikeThrough(): CharSequence = applyStyles(StrikethroughSpan())
fun CharSequence.superScript(): CharSequence = applyStyles(SuperscriptSpan())
fun CharSequence.subScript(): CharSequence = applyStyles(SubscriptSpan())
fun CharSequence.color(@ColorInt color: Int): CharSequence = applyStyles(ForegroundColorSpan(color))
fun CharSequence.click(
    paintConsumer: (TextPaint) -> Unit = {},
    clickAction: () -> Unit
): CharSequence = this.applyTags(object : ClickableSpan() {
    override fun onClick(widget: View) = clickAction.invoke()
    override fun updateDrawState(paint: TextPaint) = paintConsumer.invoke(paint)
})

fun CharSequence.click(
    clickAction: () -> Unit
): CharSequence = this.applyTags(object : ClickableSpan() {
    override fun onClick(widget: View) {
        clickAction.invoke()
    }
})

fun CharSequence.formatSpanned(vararg args: Any): SpannableStringBuilder =
    formatActual(Locale.getDefault(), this, *args)

private fun CharSequence.applyTags(vararg tags: Any): CharSequence {
    val text = SpannableStringBuilder()
    openTags(text, tags)
    text.append(this)
    closeTags(text, tags)
    return text
}

private fun openTags(text: Spannable, tags: Array<out Any>) {
    for (tag in tags) text.setSpan(tag, 0, 0, Spannable.SPAN_MARK_MARK)
}

private fun closeTags(text: Spannable, tags: Array<out Any>) {
    val len = text.length
    for (tag in tags)
        if (len > 0) text.setSpan(tag, 0, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        else text.removeSpan(tag)
}

private fun formatActual(
    locale: Locale,
    format: CharSequence,
    vararg args: Any
): SpannableStringBuilder {
    val out = SpannableStringBuilder(format)
    var start = 0
    var argAt = -1
    while (start < out.length) {
        val matcher = FORMAT_SEQUENCE.matcher(out)
        if (!matcher.find(start)) break
        start = matcher.start()
        val exprEnd = matcher.end()
        val argTerm = matcher.group(1)
        val modTerm = matcher.group(2)
        val cookedArg: CharSequence = when (val typeTerm = matcher.group(3)) {
            "%" -> "%"
            "n" -> "\n"
            else -> {
                val argIdx: Int = when (argTerm) {
                    "" -> ++argAt
                    "<" -> argAt
                    else -> Integer.parseInt(argTerm!!.substring(0, argTerm.length - 1)) - 1
                }
                val argItem = args[argIdx]
                if (typeTerm == "s" && argItem is Spanned) argItem
                else String.format(locale, "%$modTerm$typeTerm", argItem)
            }
        }
        out.replace(start, exprEnd, cookedArg)
        start += cookedArg.length
    }
    return out
}

fun CharSequence.toCurrency(locale: Locale = Locale("in", "ID")): CharSequence {
    val formatCurrency = NumberFormat.getCurrencyInstance(locale) as DecimalFormat
    val symbol = Currency.getInstance(locale).getSymbol(locale)
    val currency = this.toString().toDoubleOrNull() ?: 0
    return formatCurrency.apply {
        maximumFractionDigits = 0
        positivePrefix = "$symbol "
        negativePrefix = "-$symbol "
    }.format(currency)
}

fun CharSequence.toCurrencyNoSymbol(locale: Locale = Locale("in", "ID")): CharSequence {
    val formatCurrency = NumberFormat.getCurrencyInstance(locale) as DecimalFormat
    val currency = this.toString().toDoubleOrNull() ?: 0
    return formatCurrency.apply {
        maximumFractionDigits = 0
        positivePrefix = " "
        negativePrefix = "- "
    }.format(currency)
}

fun Int.toCurrencyNoSymbol(locale: Locale = Locale("in", "ID")): CharSequence {
    val formatCurrency = NumberFormat.getCurrencyInstance(locale) as DecimalFormat
    val currency = this.toString().toDoubleOrNull() ?: 0
    return formatCurrency.apply {
        maximumFractionDigits = 0
        positivePrefix = " "
        negativePrefix = "- "
    }.format(currency)
}
fun CharSequence.upperCase(): CharSequence {
    return this.toString().uppercase(Locale.getDefault())
}

fun CharSequence.fromHtml(): CharSequence {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(
            this.toString(),
            Html.FROM_HTML_MODE_COMPACT
        )
    } else {
        HtmlCompat.fromHtml(this.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}