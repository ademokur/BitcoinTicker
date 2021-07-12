package com.aokur.bitcointicker.ui.home.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aokur.bitcointicker.R
import com.aokur.bitcointicker.data.model.CoinDetailItem
import com.aokur.bitcointicker.data.model.FavouriteCoinModel
import com.aokur.bitcointicker.data.repository.CoinRepository
import com.aokur.bitcointicker.ui.base.BaseViewModel
import com.aokur.bitcointicker.util.Constants
import com.aokur.bitcointicker.util.Resource
import com.aokur.bitcointicker.util.Result
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(private val coinRepository: CoinRepository): BaseViewModel() {

    private val _coinInfo = MutableLiveData<Resource<CoinDetailItem>>()
    val coinInfo: LiveData<Resource<CoinDetailItem>> = _coinInfo

    private val _isFavouriteAdded = MutableLiveData<Boolean>()
    val isFavouriteAdded: LiveData<Boolean> = _isFavouriteAdded

    private val _isFavouriteDeleted = MutableLiveData<Boolean>()
    val isFavouriteDeleted: LiveData<Boolean> = _isFavouriteDeleted

    private val _isFavourite = MutableLiveData<Boolean>()
    val isFavourite: LiveData<Boolean> = _isFavourite

    val db = Firebase.firestore
        .collection(Constants.BASE_COLLECTION_NAME)
        .document(firebaseAuth.currentUser.uid)
        .collection(Constants.DETAIL_COLLECTION_NAME)

    fun getCoinByID(id: String) = viewModelScope.launch {
        coinRepository.getCoinByID(id)
            .onStart {
                _result.value = Result(loading = R.string.loading)
            }
            .catch {
                Log.v("errorGetCoinByID", it.message.toString())
            }
            .collect {
                _coinInfo.value = it
            }
    }

    fun addToFavourites(coinDetail: CoinDetailItem) {
        val favouriteCryptoModel = FavouriteCoinModel(
            coinDetail.id,
            coinDetail.image.small,
            coinDetail.name,
            coinDetail.symbol
        )

        val favouriteDocument = db.document(coinDetail.id)

        favouriteDocument.set(favouriteCryptoModel)
            .addOnSuccessListener {
                _isFavouriteAdded.postValue(true)
            }
            .addOnFailureListener {
                _isFavouriteAdded.postValue(false)
            }

    }

    fun isFavourite(cryptoID: String) {
        val favouriteDocument = db.document(cryptoID)

        favouriteDocument.get()
            .addOnSuccessListener { document ->
                _isFavourite.value = document.exists()
            }
            .addOnFailureListener { exception ->
                _isFavourite.value = false
            }
    }

    fun deleteFavourite(cryptoID: String) {
        val favouriteDocument = db.document(cryptoID)

        favouriteDocument
            .delete()
            .addOnSuccessListener { _isFavouriteDeleted.postValue(true) }
            .addOnFailureListener { _isFavouriteDeleted.postValue(false) }
    }
}