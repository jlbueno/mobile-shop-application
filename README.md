<h1><b> mobile-shop-application</b></h1>
This is a mobile shopping app that can contain multiple stores. User can shop from multiple stores while using the same shoppping cart. This is a project for my application for a developer position at MavenHive. The application will look terrible in dark mode of a mobile device.

<h2>Resources used for studying android dev</h2>
<ul>
  <li><a href="https://docs.oracle.com/javase/tutorial/java/index.html">Java Tutorials</a> by Oracle</li>
  <li><a href="https://developer.android.com/courses/fundamentals-training/toc-v2">Codelabs fro Android Developer Fundamentals</a> by Google</li>
  <li><a href="https://www.youtube.com/playlist?list=PLhQjrBD2T381qULidYDKP55-4u1piASC1">CS50 2019 - Android Track</a> by Harvard</li>
  <li><a href="https://stackoverflow.com/">StackOverflow</a></li>
</ul>

<h2>Features</h2>
<ul>
  <li>Choose the shop to buy from</li>
  <li>Add/Subtract items from the Cart</li>
  <li>"Checkout"</li>
</ul>

<h2>Run the app</h2>
Note: Using the android system's back button will mess up the state of the application because it relies on Intents to pass data around(Room is not yet implemented).<br/><br/>
<br/>

<ol>
  <li>
    <ol>
      <li>Download the project</li>
      <li>In Android Studio go to File > New > Import Project and select the project</li>
      <li>In Android Studio go to Build > Build Bundle(s) / APK(s)</li>
      <li>After generating the apk, put it on your android device and install it</li>
      <li>Run the app</li>
    </ol>
  </li>
  <li>
    <ol>
      <li>Download the APK <a href="https://drive.google.com/file/d/1WbweBMsYhQVTgZt-3awAPFeTBfZ70Lyk/view?usp=sharing">here</a>(Google Drive Link)</li>
      <li>Install the APK</li>
      <li>Run the app</li>
    </ol>
  </li>
</ol>

<h2>Technologies Used</h2>
<ul>
  <li>Android Studio 4.2.2</li>
  <li>Java 11.0.8</li>
</ul>

<h2>Dependencies</h2>
<ul>
  <li>Glide 4.12.0</li>
  <li>Gson 2.8.7</li>
</ul>

<h2>To-do</h2>
<ul>
  <li>Make a flexible layout for portrait and landscape mode in both phones and tablets</li>
  <li>Remove item from cart when quantity becomes 0</li>
  <li>Use room for data persistence</li>
  <li>Fetch shop data from an external API</li>
  <li>Add style for dark themed devices</li>
</ul>
