# WeatherMap

1. App Description
The Weather app show 5-day forecast from the current time.
The first day is named as TODAY.
The second day is named as TOMORROW.
After the second day the days are named with number. For instance, the fourth day is named DAY 4, and so on.
When launched the app will show the city name that the device location belongs to.
Users swipe left and/or right to reach to the forecast of the day they want to see.
Currently, the app show UTC time, which should be converted to the local time using java.util.Calendar library when have
 time.
Within the same day forecast, users can scroll to view the hourly forecast of every 3-hour period.

2. Scope and potential enhancements
The implementation does not focus on the UI but the architecture and the uses of design patterns and object oriented
methodology.
The app runs on Android version 4.4 or higher and multi-screen size supports.
There are no asynchronized image loading implemented but the functionality can be achieved by using volley image loader.
The free account WeatherMap API required the app to make no more than 1 API calls within every 10 minutes. The current
implementation does not follow the requirement as time is limit. One of the ways to handle that issue is to store the
timestamp and the Json data in SharePreferences. When the app is relaunched or resumes from the background it should
compute the difference between the current time and the stored one to make decision of whether or not new API calls
should be made. In case the data was lost, the stored Json can be reused.
Unit Tests were not implemented due to the limitation of time.



