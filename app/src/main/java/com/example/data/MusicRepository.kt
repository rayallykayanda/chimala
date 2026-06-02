package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class MusicRepository(private val mediaDao: MediaDao, private val eventDao: EventDao) {

    val allMediaItems: Flow<List<MediaItem>> = mediaDao.getAllMediaItems()
    val allEvents: Flow<List<ChimalaEvent>> = eventDao.getAllEvents()

    fun getMediaByType(type: String): Flow<List<MediaItem>> = mediaDao.getMediaItemsByType(type)
    fun getEventsByType(type: String): Flow<List<ChimalaEvent>> = eventDao.getEventsByType(type)

    suspend fun insertMedia(item: MediaItem) = mediaDao.insertMediaItem(item)
    suspend fun deleteMedia(item: MediaItem) = mediaDao.deleteMediaItem(item)

    suspend fun viewMedia(id: Int) = mediaDao.incrementViews(id)
    suspend fun likeMedia(id: Int) = mediaDao.incrementLikes(id)

    suspend fun insertEvent(event: ChimalaEvent) = eventDao.insertEvent(event)
    suspend fun deleteEvent(event: ChimalaEvent) = eventDao.deleteEvent(event)

    suspend fun likeEvent(id: Int) = eventDao.incrementLikes(id)

    suspend fun prepopulateIfEmpty() {
        // Check if media items are empty
        val currentMedia = allMediaItems.first()
        if (currentMedia.isEmpty()) {
            val defaultMedia = listOf(
                MediaItem(
                    title = "Chimala Yetu",
                    artist = "Mfalme wa Singeli",
                    type = "SONG",
                    category = "Singeli",
                    duration = "3:40",
                    likes = 120,
                    views = 425,
                    lyrics = "Swahili Lyrics:\n\nKaribuni Chimala, nchi ya neema na burudani!\nTuna dance, tunaimba, Singeli yetu safi ya kukata na shoka...\nChimala kwanza, Mbeya yetu kwanza!\n\n(Kiitikio)\nOya barabara ya Chimala inapita magari mengi\nLakini wasanii wetu ndio lulu kubwa zaidi!\nCheza kuisaka, cheza na kuitakatisha,\nSingeli ya chimala, inawasha inawasha!"
                ),
                MediaItem(
                    title = "Mungu Ni Mwema",
                    artist = "Chimala Gospel Choir",
                    type = "SONG",
                    category = "Gospel",
                    duration = "4:15",
                    likes = 85,
                    views = 230,
                    lyrics = "Swahili Lyrics:\n\nMungu ni mwema wakati wote,\nAtupigania mchana na usiku,\nTukiwa Chimala Mbeya tunamtukuza yeye aliyetuokoa.\n\n(Kiitikio)\nEh Bwana upokee sifa zetu,\nChimala inaimba, milima inashangilia,\nMbarali yote inainua mikono juu!"
                ),
                MediaItem(
                    title = "Nishike Mkono",
                    artist = "Sativa & Jux-style",
                    type = "SONG",
                    category = "Bongo Flava",
                    duration = "3:10",
                    likes = 150,
                    views = 680,
                    lyrics = "Swahili Lyrics:\n\nMapenzi yetu yale ya zamani,\nTukiwa kando ya barabara kuu ya Chimala,\nUsiku wa manane chini ya nyota, tabasamu lako langu pambo...\n\n(Kiitikio)\nNishike mkono, twende naye,\nHadi kilele cha milima ya Mbeya,\nChimala yetu inanoga, upendo wetu unakuwa wa dhahabu!"
                ),
                MediaItem(
                    title = "Ngoma ya Jadi (Kinyakyusa)",
                    artist = "Mwanachimala Band",
                    type = "SONG",
                    category = "Traditional",
                    duration = "4:00",
                    likes = 45,
                    views = 112,
                    lyrics = "Swahili Lyrics (Tafsiri kutoka Kinyakyusa):\n\nKinyakyusa traditional music hailing from Chimala valleys.\nSound of the drums, visual energy, and authentic Tanzanian cultural dance.\n\n(Kiitikio)\nInueni miguu, gonga chini kwa nguvu\nSauti ya zeze inaita, utamaduni mkuu wa Chimala!"
                ),
                MediaItem(
                    title = "Chimala High Energy (Live Concert Video)",
                    artist = "Mfalme wa Singeli",
                    type = "VIDEO",
                    category = "Singeli",
                    duration = "2:50",
                    likes = 312,
                    views = 1542,
                    lyrics = "Video inayoonyesha jinsi vijana wa Chimala wanavyojituma katika uchezaji na uhamasishaji wa muziki wa Singeli barabarani."
                ),
                MediaItem(
                    title = "Mbeya Green Valleys (Official Video)",
                    artist = "Sativa",
                    type = "VIDEO",
                    category = "Acoustic",
                    duration = "3:55",
                    likes = 198,
                    views = 980,
                    lyrics = "Safari ya picha yenye kuvutia sana inayopita katika mabonde ya kijani ya Chimala, mashamba ya mpunga ya Mbarali na milima mirefu ya Mbeya."
                ),
                MediaItem(
                    title = "Sifa Kuu Live Concert",
                    artist = "Chimala Gospel Choir",
                    type = "VIDEO",
                    category = "Gospel",
                    duration = "5:20",
                    likes = 870,
                    views = 3420,
                    lyrics = "Kurekodiwa kwa tamasha kubwa la sifa lililofanyika katika viwanja vya Chimala na kuhudhuriwa na maelfu ya watu kutoka Mbarali."
                ),
                MediaItem(
                    title = "Swangila Traditional Dance",
                    artist = "Mwanachimala Band",
                    type = "VIDEO",
                    category = "Traditional",
                    duration = "4:30",
                    likes = 95,
                    views = 512,
                    lyrics = "Video ya miondoko ya ngoma ya asili ya milima ya Mbeya yenye vazi la asili, iliyorekodiwa katika maporomoko ya maji karibu na Chimala."
                )
            )
            for (media in defaultMedia) {
                insertMedia(media)
            }
        }

        // Check if events/news are empty
        val currentEvents = allEvents.first()
        if (currentEvents.isEmpty()) {
            val defaultEvents = listOf(
                ChimalaEvent(
                    title = "Chimala Music Festival 2026",
                    description = "Tamasha kubwa la kila mwaka linalowaleta pamoja wasanii wote wa Singeli, Bongo Flava, na Gospel kutoka Mbeya. Usikose burudani hii ya kihistoria na fursa za kukuza wasanii chipukizi!",
                    date = "Julai 15, 2026",
                    location = "Uwanja wa Polisi Chimala, Mbeya",
                    price = "Tsh 10,000",
                    type = "EVENT",
                    organizer = "Tanzania Music Association - Mbeya",
                    likes = 345
                ),
                ChimalaEvent(
                    title = "Usiku wa Singeli Chimala",
                    description = "Sherehe kubwa na mashindano ya kucheza Singeli. Zawadi nono zitatolewa kwa mchezaji bora na msanii bora wa kienyeji. Kutakuwa na chakula na vinywaji vya asili.",
                    date = "Agosti 29, 2026",
                    location = "Mbeya Highway Club, Chimala",
                    price = "Tsh 5,000",
                    type = "EVENT",
                    organizer = "Kipepeo Entertainment",
                    likes = 189
                ),
                ChimalaEvent(
                    title = "Mkesha Mkuu wa Sifa Chimala",
                    description = "Usiku wa kusifu na kuabudu na huduma za maombi. Vikundi mbalimbali vya Gospel kutoka Chimala, Rujewa, na mkoa mzima wa Mbeya vitashiriki kikamilifu katika kumuimbia Bwana.",
                    date = "Desemba 31, 2026",
                    location = "Kanisa la Kiinjili Chimala",
                    price = "Bure",
                    type = "EVENT",
                    organizer = "Chimala Joint Churches Council",
                    likes = 278
                ),
                ChimalaEvent(
                    title = "Alikiba Kuhudhuria Tamasha la Chimala?",
                    description = "Habari za hivi punde zinasema mazungumzo yanaendelea kumleta mkongwe wa Bongo Flava, Alikiba kama mgeni rasmi katika kilele cha tamasha la sanaa Chimala kitakachofanyika mwezi huu. Hii ni fursa adhimu kwa wasanii wadogo kukutana naye na kupata ushauri wa kimuziki.",
                    date = "Juni 12, 2026",
                    location = "Mbeya News Network",
                    price = "Habari za Matamasha",
                    type = "NEWS",
                    organizer = "E-FM Mbeya Bureau",
                    likes = 412
                ),
                ChimalaEvent(
                    title = "Kikundi cha Jadi Chimala Chashinda Tuzo",
                    description = "Kikundi cha ngoma za asili kutoka Chimala kimejishindia tuzo ya kikundi bora cha utamaduni katika mkoa wa Mbeya. Tuzo hiyo imekabidhiwa na Mkuu wa Mkoa kufuatia juhudi zao za kudumisha mila na kuelimisha jamii kupitia nyimbo za kiasili.",
                    date = "Juni 02, 2026",
                    location = "Halmashauri ya Mbarali",
                    price = "Tuzo na Utamaduni",
                    type = "NEWS",
                    organizer = "Mbarali District Media",
                    likes = 95
                )
            )
            for (event in defaultEvents) {
                insertEvent(event)
            }
        }
    }
}
