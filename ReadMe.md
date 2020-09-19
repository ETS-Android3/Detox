# Detox

Detox is an Android application to help people who suffer from high blood pressure, high blood sugar and high cholesterol to mitigate the risk of getting the complications or exacerbate the illness. Through our application, they can get the ingredients of products by searching the products or scanning the bar code. Meanwhile, they can check what kind of ingredients are harmful to them and get some healthy tips. In addition, they can also find the nearby health centre through our map function. 

## Installation

### Project dependencies

Make sure you have the following major software installed:

- Android Studio

Other setting of Android Studio:

- API level: higher than 28

### Run Detox on the local machine by Android Studio

1. Clone or download the project into local machine.
2. Open the project in the Android Studio and choose the Android API that higher than 28.
3. Setting the virtual device (recommend the Pixel 2 or 3).
4. Click the 'run app' button to run the project.
5. Waiting for finishing the virtual device setting, you can run and test our app.

### Run Detox on your Android mobile phone

1. Clone or download the project into local machine.
2. Open the project in the Android Studio and make sure that the Android API is higher than 28.
3. Click the 'build' button on the tool bar at the top of the window, choose the 'build bundle(s)/APK(s)' and click the 'build APK(s)'
4. Find the generated APK in your computer and put the APK into your mobile phone
5. Download and install the app on your mobile phone

## Application user guide

### Home screen

- click the 'GET START NOW' button can go to the search screen
- click the 'health tips' button can go to the Health tips screen
- click the 'health centre' button can go to the map screen, which may be implemented in iteration 3
- you can go to the each screen by clicking the icon in bottom navigation bar

### Search screen

- type the ingredient you want to search in the search bar and click the 'search' button
- click one of the product (either the image or text) will jump into the ingredient screen

### Category screen

- select one of the category in the screen and the product list will show
- select the ingredient and level to filter the products
- click one of the product (either the image or text) will jump into the ingredient screen

### Ingredient Screen

- click the spinner to choose the illness you suffer and see what kind of ingredients are harmful to you

### Scan Screen

- To run this functionality well, make sure you agree to camera permission
- click the 'scan' button to scan the bar code of products
- after get the bar code, click the 'search' button to jump into the ingredient screen

