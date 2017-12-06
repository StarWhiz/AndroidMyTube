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

Using Emulator ...........

PART I (Specific to our app)
******
First hit Tools > Firebase > then on the side bar click Authentication...
Then where it says connect your app to firebase do so.
use the following developer account

email: dbzradar137group@gmail.com 
pw: +zo&Bs,ynFi~~:NHhKm^

PART II (Should be the same for other groups besides us ...)
*******
Just do File > Open... and select the project folder and select the project it contains. and click ok.
After all gradle syncs are complete. And all updates are done.
Make sure under Tools -> Android -> Android SDK Manager -> under the SDK Tools Tab... that the following
are installed...
	1) Android SDK Tools
	2) Google Play services
This app worked using the Nexus 5 emulator API 24 (Android 7.0 w/ Google Play).
If using different API level for emulator.... Make sure the API level chosen has
Google Play otherwise it will not work.

Then Do Run > 'Run app'


Using Phone ...........

Alternatively, install the .apk file onto your own Android phone by copying it to your phone. Then use 
a dedicated file manager for your phone like 'ES File Explorer' and find the apk you copied. Then install
it.

Google Play Services and Youtube must exist on your phone for this app to function. It's the basic
requirement of using the Youtube API's from Google.

Basic App Usage
*****************************************************************************************************
1. Click the Google 'Sign in' button and sign in with your selected Google account.
2. The favorites page is the main page it displays playlist called "SJSU-CMPE-137" or creates it if you don't have one yet.
3. To search for videos tap the search tab. Then Type in some search terms in the search bar... then tap the magnifying glass. It will take a while for videos to load ~ 5-10 seconds.
4. To play a video you searched... tap on the picture of the video you want to play.
5. To add a video to you favorites playlist called 'SJSU-CMPE-137' press the heart icon next to that searched video.
	It should be filled now. To unfavorite the same video on the spot repeat the same action.
6. See the updates to your favorites playlist by tapping the favorites tab which is next to the search tab.
7. Here you can delete videos from your favorites playlist by tapping the trash can icon next to the video you want to delete.
8. You can also play videos from your favorites playlist by tapping the video thumbnail.
9. To Logout press the "Log Out" button on the bottom.


Contributions
*************
Tai did the Favorites tab, VideoPlayer activity, VideoAdapter for recyclerView, and, youtubeConnector (to communicate with google youtube APIs). Implemented deletion of videos from playlists. 

Jose did most of the searching youtube video features. Helped me with video deletion from playlist

Nathan did the google sign-in and authorization. Created the myVideo data structure.

Chastin did the navigation layout and a little bit of the searching video features.

Comments
********
We did our best with deletion please don't deduct points for not doing it the same way as specified in the lab description (with multiple check boxes). Plus our app didn't have an action bar to begin with. It was the hardest part and we got deletion working in a similar way. Please accept this alternative it's still pretty fast to delete multiple videos as you can see.

Thank you!