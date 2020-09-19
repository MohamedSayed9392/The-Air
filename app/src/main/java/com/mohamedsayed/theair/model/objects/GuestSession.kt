import com.google.gson.annotations.SerializedName

data class GuestSession (

	@SerializedName("guest_session_id") val id : String,
	@SerializedName("expires_at") val expires_at : String
)