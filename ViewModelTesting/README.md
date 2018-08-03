
# ViewModel Testing Examples

This project contains two ViewModel classes, one extends from **ViewModel** and the other extends from **Android ViewModel**, both classes
have the same method that sums a vararg of Integers.

This repository shows how to perform Unit Tests on them, the ViewModel class is quite trivial as it doesn't require any overload for its
initialization but the AndroidViewModel class is a bit more tricky as it requires an **Application** instance for calling its constructor.

There are two solutions for this problem with the AndroidViewModel class.

 - Use **Robolectric** to retrieve an instance of the Application class, this allow the test class to exist in the test directory.
 - Parse the **InstrumentationRegistry applicationContext** as an Application instance, this will need the class to exist in the androidTest directory and will require the use of a physical device or an emulator.

**special thanks to [Bruno Coelho (@4brunu)](https://stackoverflow.com/users/976628/bruno-coelho)**

_[Here](https://stackoverflow.com/questions/51487892/unit-testing-androidviewmodel-classes) you can find a link to my original question in StackOverflow._


