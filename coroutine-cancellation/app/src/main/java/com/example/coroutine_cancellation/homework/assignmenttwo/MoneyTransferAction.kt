package com.example.coroutine_cancellation.homework.assignmenttwo

sealed interface MoneyTransferAction {
    data object TransferFunds : MoneyTransferAction
    data object CancelTransfer : MoneyTransferAction
}