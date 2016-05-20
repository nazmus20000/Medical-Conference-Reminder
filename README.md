# Medical-Conference-Reminder
An Android application that provides managing and tracking of medical conference of doctors.

<h2>Functional Specification</h2>

An Android application that doctors can use to participate in and track medical conference schedules.

<ul>
    <li>
    For conference admins
        <ul> 
            <li>can schedule new conferences</li>
            <li>can send conference calendar invites to doctors</li>
            <li>can see suggested new/old topics</li>
        </ul>
    </li>
</ul>
<ul>
    <li>
    For doctors
        <ul> 
            <li>can suggest new topics they could speak on</li>
            <li>can see suggested new/old topics</li>
            <li>can receive / accept and reject invites</li>
            <li>invites are added to calendar</li>
        </ul>
    </li>
</ul>
    
<h2>Technologies Used</h2>
  
  1. Android Studio
  
<h2>Technical Specifications</h2>

  1. For simplicity the application is standalone and uses SQLite as content provider to manage topics and schedules (later this could 
     be connected to a web service, but for now it works on one device only)
  2. User could log in either as admin or doctor to test application
  3. Default admin 
      email: admin@g.com 
      password: 12345678
      (Automatically Created)

<h2>Description </h2> 
<span>
<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2206361.png" border="0" width="200" height="350"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2207051.png" border="0" width="200" height="350"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2207421.png" border="0" width="200" height="350"/>
</span>

Here both in the login and registration page, the email must be at somthing@something.something format. And the password must be atleast of size 8.

<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2207531.png" border="0" width="200" height="350"/>

Image can be uploaded from phone memory.

<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2208031.png" border="0" width="200" height="350"/>

Here is the users dashboard.

<span>
<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2208441.png" border="0" width="200" height="350"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2208511.png" border="0" width="200" height="350"/>
</span>

Different topics can be added by the users.

<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2208561.png" border="0" width="200" height="350"/>

User can see the new/old topics that is published by him/herself or other doctors.

<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2209051.png" border="0" width="200" height="350"/>

Users can eloborate, edit or delete a particular topic. (Edit or delete is allowed only if it is written by that user)

<span>
<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2209551.png" border="0" width="200" height="350"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2210031.png" border="0" width="200" height="350"/>
</span>

User can see all the invitations invited by the admins and also can reject/accept the invitation. If they accept it then a schedule is set in the calander of the mobile phone.

<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2210361.png" border="0" width="200" height="350"/>

Here is the admin dashboard.

<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2210551.png" border="0" width="200" height="350"/>

Admins can schedule any event for the doctors.

<span>
<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2211141.png" border="0" width="200" height="350"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2211181.png" border="0" width="200" height="350"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2211231.png" border="0" width="200" height="350"/>
</span>

Admin can invite anyone to a particular event. Admin can also see the status of the invitaion. It can be pending, going and not going status. 

<img src="https://nazmussakibsite.files.wordpress.com/2016/05/screenshot_20160519-2211411.png" border="0" width="200" height="350"/>

There is also a navigation drawer where anyone can sign out and can see their images and name.


