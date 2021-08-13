![banner](https://github.com/JulianNSH/MoneyManager/blob/main/screenshots/mm_logo.png)


About
------
&nbsp; &nbsp; &nbsp;&nbsp;
Android application that provides to the users a solution for managing personal finances through an accessible and effective tools of organizing and providing data on their own finances and savings.

Structure/Features
------

### Menu
&nbsp; &nbsp; &nbsp;&nbsp;
Each page of the application will be created as fragments in its main view. The pages will be accessed by typing the icons corresponding to each page on the bottom bar, its visual model is presented below. This will allow the user to access any segment of the application directly, regardless of the current page opened by him at the current stage. <br><br>
![alt text](https://github.com/JulianNSH/MoneyManager/blob/main/screenshots/bottom_menu.png "Bottom Menu")


### Add
&nbsp; &nbsp; &nbsp;&nbsp;  The basic fragment of the application, which will be accessed primarily at the first start of the application, is the page of adding data about income, expenses and scopes, obviously without this input data the rest of the pages are useless because they can not provide a some informative content. <br>

![alt text](https://github.com/JulianNSH/MoneyManager/blob/main/screenshots/add_in.png "Income") &nbsp;&nbsp;![alt text](https://github.com/JulianNSH/MoneyManager/blob/main/screenshots/add_oth.png "Outgoing")&nbsp;&nbsp;![alt text](https://github.com/JulianNSH/MoneyManager/blob/main/screenshots/add_out.png "Scopes")

&nbsp; &nbsp; &nbsp;&nbsp; On tap on add button from the bar menu, will appear a dialog box containing a TabView with those 3 categories of data input that can be accessed by tapping or swipe. <br>

&nbsp; &nbsp; &nbsp;&nbsp; In accordance with the category selected to the user in the form for addition, 5 fields are exposed for data entry, of which the first 3 are mandatory and the last 1 additional:
 - **_Sursa_** - Requires a _String_ that will name the entered data entity.
 - **_Suma_** - _Float_ numeric value for transaction cash.
 - **_Ora si data_** - Respectively the time / period when the transaction took place (it does not necessarily correspond to the time and date of the introduction). Two divided fields with manual or automatic entry on double tap, in which the dialog boxes with time and data-picker will appear.
 - **_Comentariul_** - Textual field, same type _String_, for additional information if the user will need it.


### Sinopsis
&nbsp; &nbsp; &nbsp;&nbsp; Provides the main information about the status of the user's finances in the current time period, or the one selected by him individually, also helps user to realize the situation in other time periods highlighting the generalization of revenues and expenses, highlighted in bar chart, thus facilitating the activity of comparative analysis of the user in terms of the situation in different time segments. <br>

![alt text](https://github.com/JulianNSH/MoneyManager/blob/main/screenshots/overview_1.png "Overview")&nbsp;&nbsp;&nbsp;&nbsp;![alt text](https://github.com/JulianNSH/MoneyManager/blob/main/screenshots/overview_2.png "Card View")

&nbsp; &nbsp; &nbsp;&nbsp; In the header of the page, there is a navigator composed of three buttons that allow the display of data from different months and years, of course if there has registered data. <br>
&nbsp; &nbsp; &nbsp;&nbsp; Next is the segment with total income, total expenses and its percentage ratio for selected period. This representation of data aims to simplify and summarize the total data, and the indicator will reflect the situation between these two polarities. The functional logic of the indicator is quite simple, it achieves in some correspondence with the principle "50-20-30". Thus, the percentage ratio between the user's income and expenses is achieved, if its result is less than 50%, the indicator will be green, if the result is between 50% -80% - yellow, and if it is greater than 80% - red, saving the user from performing the additional calculations.<br>
&nbsp; &nbsp; &nbsp;&nbsp; Below, the bar chart, which is divided into 7 segments on the x-axis corresponding to the months, within which are placed 2 bars that represent the total income and expenses of these months. Centered is the month selected through the navigator above, and in parts, divided into three, the months preceding and succeeding the selected one. This representation provides information about the financial situation in other months, without the need to access them in the absence of a prominent need, but also outlines an image of economic activities in the other months.
It is important to note that this and other charts in this project were made using the [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) library provided as free access by [Philipp Jahoda](https://github.com/PhilJay) on his GitHub page. <br>
&nbsp; &nbsp; &nbsp;&nbsp; Below that is a _CardView_, at a swipe-up the user can highlight the list of transactions made during selected period. In the header of this CardView the user has at his disposal several options for sorting the contents of this list, namely sorting by **_time_** **_period_** in ascending and descending order, sorting by cash **_value_**, both ascending and descending, sorting **_alphabetically_** and in the reverse order of the alphabet. Also, the user can interact directly with existing entities. When tap on the desired item, a dialog box will appear through which the user can _modify_ or _delete_ the entered data.

### Statistica
&nbsp; &nbsp; &nbsp;&nbsp; It is meant to group the user's expenses and provide information about them through a useful and convenient graphical model for data collection.

![alt text](https://github.com/JulianNSH/MoneyManager/blob/main/screenshots/stat_1.png "Statistics")&nbsp;&nbsp;&nbsp;&nbsp;![alt text](https://github.com/JulianNSH/MoneyManager/blob/main/screenshots/stat_2.png "Recycler View")

&nbsp; &nbsp; &nbsp;&nbsp; Like the previous page, in the header is present the selector for the time periods necessary for the user, for the respective specification of the period of the data that the user especially needs.<br>

&nbsp; &nbsp; &nbsp;&nbsp; The selector is followed by the main segment of this page, the pie chart. Its number of partitions may be different, depending on the number of categories of expenses incurred by the user during that period, but their number will never exceed 6 partitions. If the user has spent in more than 6 categories, the application will select only 5 with the largest volume of cash, and all the others will be added to the 6th. This limitation in the number of fragments of the diagram is meant to emphasize the user's attention only on the most voluminous expenses, to highlight the share of the total volume of expenses and to avoid segmenting the diagram into a large number of partitions with insignificant percentage values. <br>
This diagram was also made by using the [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) library.<br>


### Economii
&nbsp; &nbsp; &nbsp;&nbsp; Provides information on all data about expenses and income from all periods of time held. More specifically, it focuses on the total summary value of the difference between income and expenditure for different months but also the summary of these months as a whole. Thus the user will have the information about the volume of free finances he holds at the current stage.

![alt text](https://github.com/JulianNSH/MoneyManager/blob/main/screenshots/saving_1.png "Savings")&nbsp;&nbsp;&nbsp;&nbsp;![alt text](https://github.com/JulianNSH/MoneyManager/blob/main/screenshots/saving_2.png "Recycler View")
<br>
&nbsp; &nbsp; &nbsp;&nbsp; The line chart show savings for each month. The number of months, which is on the X-axis, depends on their dates and periods. But the chart is scalable and the user can navigate on both axes, thus highlighting for himself the group of months that interests him and the volume of finances left after them. <br>
Line chart was also made by using the [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) library.<br>


### Scopuri
Here the user can add the material scopes that he wants to achieve but does not have the necessary savings at the moment, or has these savings but wants to record the fact of their use for specific purposes.

![alt text](https://github.com/JulianNSH/MoneyManager/blob/main/screenshots/scopes.png "Scopes")&nbsp;&nbsp;&nbsp;&nbsp;![alt text](https://github.com/JulianNSH/MoneyManager/blob/main/screenshots/scope_info.png "Info")
<br>
&nbsp; &nbsp; &nbsp;&nbsp; Once the scope is created it will appear in the scopes list. To allocate the volumes of finance for this scope the user must access the sequence "Scopuri" on the "Adaugare" dialog, there will be available the list of scopes created, from which the user will choose the desired and similar to adding income and expenses will complete with the necessary data. With the addition of the amount for the purpose, in the list of purposes, to the respective item, the amount collected will change, approaching the necessary one. When the required amount is collected, then this option of confirming/completing the scope becomes available. By typing it the user will confirm that the scope has been achieved and that the funds collected have been used for this scope. At this time all data entered at the stage of allocating finance to savings will be transferred to spendings. It should be noted that so far, the finance introduced by allocation, for scopes, did not influence the statistical data on the financial situation, because the user in principle has not yet used the given finance and/or can use it elsewhere as needed. However, once the desired purpose has been completed, he can confirm this fact and all the amounts allocated for this scope will be transferred to the spendings, keeping the period in which they were allocated.

------
* Once again, [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) by [Philipp Jahoda](https://github.com/PhilJay), helps me a lot 💙 <br>
* The logo was made using the online [TailorBrands](https://www.tailorbrands.com/logo-maker) tool.
