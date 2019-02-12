## JavaFX : FXML

```xml
<!-- A Label --> 
<Label text="Hello World"/>

<!-- make use of com.sum.javafx.fxml.BeanAdapter -->

<!-- you can construct a wrapped instance if it implements valueOf() -->
<String fx:value="Hello World"/>
<Double fx:value="1.0"/>
<Boolean fx:value="false"/> 

<!--creates an object from another FXML file -->
<fx:include source="anotherfxml.fxml"/>
<!-- children tag is read-only properties-->

<!- the children property is default property so children tag is not needed -->
<VBox xmlns:fx="http://javafx.com/fxml">
    <Button text="Click Me!"/>
</VBox>

```

