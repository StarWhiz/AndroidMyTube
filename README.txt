DBZ Project Members
*****************************************************************************************************
Name: Tai Dao
SID: 006769100
Email: taidao90@gmail.com

Name: Jose Quan
SID: 009297119
Email: josejaimeq@gmail.com

Name: Nathan Lin
SID: 009942621
Email: nathanlin2046@gmail.com

Name: Chastin Gammage
SID: 0008419333
Email: chastin.gammage@gmail.com


How to Build App
*****************************************************************************************************

Just do File > Open... and select the project folder and select the project it contains. and click ok.
After all gradle syncs are complete. And all updates are done.
Do Run > 'Run app'

Alternatively, install the .apk file onto your own Android phone by copying it to your phone. Then use 
a dedicated file manager for your phone like 'ES File Explorer' and find the apk you copied. Then install
it.

Google Play Services and Youtube must exist on your phone for this app to function. It's the basic
requirement of using the Youtube API's from Google.

Basic App Usage
*****************************************************************************************************
1. Click the Google 'Sign in' button and sign in with your selected Google account.
2. The search page is the main page.
3. To search for videos type in some search terms in the search bar... then tap the magnifying glass.
	It will take a while for videos to load ~ 5-10 seconds.
4. To play a video you searched... tap on the picture of the video you want to play.
5. To add a video to you favorites playlist called 'SJSU-CMPE-137' press the heart icon next to that searched video.
	if this one does by mistake user can unfavorite by repeating the same action.
6. To view your favorites playlist called "SJSU-CMPE-137" click on the favorites tab next to the search tab.
7. To delete videos from your favorites playlist just press on the trash can icon on the items you want to delete.
8. To Logout press the "Log Out" button on the bottom.


Contributions
*************
Tai did the Favorites tab, VideoPlayer activity, VideoAdapter for recyclerView, and, youtubeConnector (to communicate with google youtube APIs). Implemented deletion of videos from playlists. 

Jose did most of the searching youtube video features. Helped me with video deletion from playlist

Nathan did the google sign-in and authorization. Created the myVideo data structure.

Chastin did the navigation layout and a little bit of the searching video features.

Comments
********
We did our best with deletion please don't deduct points for not doing it the same way as specified in the lab description (with multiple check boxes). Plus our app didn't have an action bar to begin with. It was the hardest part and we got deletion working in a similar way. Please accept this alternative.

thank you!
