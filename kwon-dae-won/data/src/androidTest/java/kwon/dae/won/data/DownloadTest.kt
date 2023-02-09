package kwon.dae.won.data

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat.registerReceiver
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kwon.dae.won.data.usecase.DownloadUseCase
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * @author soohwan.ok
 * @since
 */
class DownloadTest {


    @Test
    fun downloadTest() {
        val countDownLatch = CountDownLatch(1)

        val id = 52677539752
        val secret = "6d706554fb"
        val serverId = 65535
        val url = "https://live.staticflickr.com/${serverId}/${id}_${secret}.jpg"
        val desc = "Corrida da turma Bravo - Primeira turma feminina do Colégio Naval 2023"

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val downloadUseCase = DownloadUseCase(appContext)

        var receivedId: Long = -1L
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.let { receivedId = it.getLongExtra("extra_download_id", receivedId) }
                countDownLatch.countDown()
            }
        }
        appContext.registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        val downloadedId: Long = downloadUseCase("${id}_${secret}.jpg", desc, url)
        countDownLatch.await(10, TimeUnit.SECONDS)
        appContext.unregisterReceiver(receiver)
        assert(downloadedId == receivedId)
    }
}
