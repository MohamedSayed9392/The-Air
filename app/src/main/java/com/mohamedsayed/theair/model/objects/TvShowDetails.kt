import com.google.gson.annotations.SerializedName

data class TvShowDetails (

	@SerializedName("backdrop_path") val backdrop_path : String,
	@SerializedName("created_by") val created_by : List<CreatedBy>,
	@SerializedName("episode_run_time") val episode_run_time : List<Int>,
	@SerializedName("first_air_date") val first_air_date : String,
	@SerializedName("genres") val genres : List<Genres>,
	@SerializedName("homepage") val homepage : String,
	@SerializedName("id") val id : Int,
	@SerializedName("in_production") val in_production : Boolean,
	@SerializedName("languages") val languages : List<String>,
	@SerializedName("last_air_date") val last_air_date : String,
	@SerializedName("name") val name : String,
	@SerializedName("next_episode_to_air") val next_episode_to_air : String,
	@SerializedName("networks") val networks : List<Networks>,
	@SerializedName("number_of_episodes") val number_of_episodes : Int,
	@SerializedName("number_of_seasons") val number_of_seasons : Int,
	@SerializedName("origin_country") val origin_country : List<String>,
	@SerializedName("original_language") val original_language : String,
	@SerializedName("original_name") val original_name : String,
	@SerializedName("overview") val overview : String,
	@SerializedName("popularity") val popularity : Double,
	@SerializedName("poster_path") val poster_path : String,
	@SerializedName("status") val status : String,
	@SerializedName("type") val type : String,
	@SerializedName("vote_average") val vote_average : Double,
	@SerializedName("vote_count") val vote_count : Int
)