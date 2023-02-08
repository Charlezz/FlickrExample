package hello.com.pose

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.com.pose.shared.domain.photo.FetchPhotoListUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchPhotoListUseCase: FetchPhotoListUseCase
) : ViewModel() {

}