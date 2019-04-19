# TIPSEASE
This README outlines the processes used to build the Android application for the Tipsease Lambda Build Week project. The application is a tipping service that allows users to instantly tip service workers by searching for their profile. It also allows service workers to upload a few personal details as well as view their tips

## API, DAO, and NetworkAdapter
- All functions and methods related to accessing the API can be found in the UserDAO class. The API stores information related to customers and service workers who have registered with the application
- The DAO methods are designed to return JSON responses along with successful response codes (200 and 201), which are used to validate every use-case to make Toasts
- NetworkAdapter class is used to make HTTPRequests for JSON and Bitmap responses. The class has a getResponseCode() method which is deprecated and will be removed

### Login Page
- The application begins with the login page, controlled by the LoginActivity class. Users can login or register an account. Registration leads user to the appropriate DialogFragment class to register the user
- The user receives  a token upon logging in, which is stored in SharedPreferences and sent as a header with every subsequent CRUD request
- The token is valid for 1 hour and every subsequent boot of the application after the initial manual login initiates an auto-login for the hour
- Interaction with any logout button in app clears SharedPreferences and redirects to login page

### Customer Home Page
- Customer home page is an Activity, which consists of a simple EditText search functionality. The page also contains a RecyclerView controlled by an EmployeeListAdapter. The activity sends a GET request to obtain all employees and filters that list based on user input. 
- The EmployeeListAdapter populates the RecyclerView with CardViews of employees with their basic information and image

### Employee Details Page
- This page is a Fragment that contails an employees details, image, a tipping button, a RatingBar  and a logout button. The tip button opens a DialogFragment with a basic tip calculator
- RatingBar and tip calculator return Toasts when the API request is successful

### Employee Home Page
- Employee home page is an Activity, which contains two navigation buttons as well as a logout button. The page als contains a header of the employee's name.
- The edit navigation buttons directs user to a fragment, which contains a form that auto-populates with the employee's previously inputed information
- The history navigation button leads to a TippingHistoryActivity that contains a RecyclerView, account balance, and a transfer to bank button
- Transfer to bank button clears the account balance and creates a ticket in the back end to transfer the balance to the employee's bank/paypal account 
