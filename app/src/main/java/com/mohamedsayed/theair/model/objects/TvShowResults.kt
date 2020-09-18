import com.google.gson.annotations.SerializedName
import com.mohamedsayed.theair.model.objects.TvShow

data class TvShowResults (
	@SerializedName("page") val page : Int,
	@SerializedName("results") val results : List<TvShow>
)