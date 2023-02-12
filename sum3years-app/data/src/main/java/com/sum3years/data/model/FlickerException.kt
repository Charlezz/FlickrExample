package com.sum3years.data.model

sealed class FlickerException(override val message: String) : Exception(message) {
    object TooManyTagsInQuery : FlickerException("Too many tags in ALL query")
    object UnknownUser : FlickerException("Unknown user")
    object Parameterless : FlickerException("Parameterless searches have been disabled")
    object PermissionToView : FlickerException("You don't have permission to view this pool")
    object UserDeleted : FlickerException("User deleted")
    object ServerError :
        FlickerException("Sorry, the Flickr search API is not currently available.")

    object NoValidMachineTags : FlickerException("No valid machine tags")
    object ExceededMaxAllowableMachineTags :
        FlickerException("Exceeded maximum allowable machine tags")

    object EscapeSearchContacts : FlickerException("You can only search within your own contacts")
    object IllogicalArguments : FlickerException("Illogical arguments")
    object InvalidApiKey : FlickerException("Invalid API Key")
    object ServiceCurrentlyUnavailable : FlickerException("Service currently unavailable")
    object WriteOperationFailed : FlickerException("Write operation failed")
    object FormatNotFound : FlickerException("Format \"xxx\" not found")
    object MethodNotFound : FlickerException("Method \"xxx\" not found")
    object InvalidSOAPEnvelope : FlickerException("Invalid SOAP envelope")
    object InvalidXMLRPCMethodCall : FlickerException("Invalid XML-RPC Method Call")
    object BadURLFound : FlickerException("Bad URL found")
    object UnknownError : FlickerException("Unknown Error")

    companion object {

        fun fromCode(code: Int): FlickerException {
            return when (code) {
                1 -> TooManyTagsInQuery
                2 -> UnknownUser
                3 -> Parameterless
                4 -> PermissionToView
                5 -> UserDeleted
                10 -> ServerError
                11 -> NoValidMachineTags
                12 -> ExceededMaxAllowableMachineTags
                17 -> EscapeSearchContacts
                18 -> IllogicalArguments
                100 -> InvalidApiKey
                105 -> ServiceCurrentlyUnavailable
                106 -> WriteOperationFailed
                111 -> FormatNotFound
                112 -> MethodNotFound
                114 -> InvalidSOAPEnvelope
                115 -> InvalidXMLRPCMethodCall
                116 -> BadURLFound
                else -> UnknownError
            }
        }
    }
}
