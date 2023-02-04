package kwon.dae.won

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kwon.dae.won.data.retrofit.FlickrRetrofitFactory
import kwon.dae.won.data.usecase.DownloadUseCase
import kwon.dae.won.domain.usecase.GetRecentUseCase
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FlickrViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getRecentUseCase: GetRecentUseCase,
    private val downloadUseCase: DownloadUseCase
) : ViewModel() {

}