package otus.homework.reactivecats

import android.content.Context
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.logging.Handler
import kotlin.random.Random

class LocalCatFactsGenerator(
    private val context: Context
) {

    /**
     * Реализуйте функцию otus.homework.reactivecats.LocalCatFactsGenerator#generateCatFact так,
     * чтобы она возвращала Fact со случайной строкой  из массива строк R.array.local_cat_facts
     * обернутую в подходящий стрим(Flowable/Single/Observable и т.п)
     */
    fun generateCatFact(): Single<Fact> {
        val index = Random.nextInt(5)
        val stringToFacts = context.resources.getStringArray(R.array.local_cat_facts)[index]
        return Single.just(Fact(stringToFacts))
    }


    /**
     * Реализуйте функцию otus.homework.reactivecats.LocalCatFactsGenerator#generateCatFactPeriodically так,
     * чтобы она эмитила Fact со случайной строкой из массива строк R.array.local_cat_facts каждые 2000 миллисекунд.
     * Если вновь заэмиченный Fact совпадает с предыдущим - пропускаем элемент.
     */
    fun generateCatFactPeriodically(): Flowable<Fact> {
        val resourcesArray = context.resources.getStringArray(R.array.local_cat_facts)
        return Flowable
            .interval(2000, TimeUnit.MILLISECONDS, Schedulers.io())
            .map { Fact(resourcesArray[Random.nextInt(5)]) }
            .distinctUntilChanged()
            .onBackpressureDrop()
    }
}