import com.google.gson.annotations.SerializedName

data class DefaultResponse (

	@SerializedName("status") val status : Boolean,
	@SerializedName("status_code") val status_code : Int,
	@SerializedName("status_message") val status_message : String
)