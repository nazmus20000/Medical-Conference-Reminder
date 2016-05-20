# Medical-Conference-Reminder
An Android application that provides managing and tracking of medical conference of doctors.

<h2>Functional Specification</h2>

An Android application that doctors can use to participate in and track medical conference schedules.

1. For conference admins
    a. can schedule new conferences
    b. can send conference calendar invites to doctors
    c. can see suggested new/old topics
2. For doctors
    a. can suggest new topics they could speak on
    b. can see suggested new/old topics
    c. can receive / accept and reject invites
    d. invites are added to calendar
    
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

