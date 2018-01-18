Action Tooltip [![Maven Central](https://img.shields.io/badge/Maven%20Central-spinner--preference-brightgreen.svg)](http://search.maven.org/#search%7Cga%7C1%7Ckostasdrakonakis) [ ![Download](https://api.bintray.com/packages/kdrakonakis/maven/tooltip-action/images/download.svg) ](https://bintray.com/kdrakonakis/maven/tooltip-action/_latestVersion)


A custom Tooltip that can be anchored to a view. By default it supports TextView only but you can setCustomView with your custom layout.xml file.

![alt tag](https://github.com/kostasdrakonakis/tooltip_action/blob/master/button_tooltip.PNG)
![alt tag](https://github.com/kostasdrakonakis/tooltip_action/blob/master/menu_item.PNG)


Download
--------

Download the latest JAR or grab via Maven:
```xml
<dependency>
  <groupId>com.github.kostasdrakonakis</groupId>
  <artifactId>tooltip-action</artifactId>
  <version>1.2.0</version>
</dependency>
```
or Gradle:
```groovy
implementation 'com.github.kostasdrakonakis:tooltip-action:1.2.0'
```

Usage
-----

```java
ActionTooltip.anchorAt(this, findViewById(R.id.my_anchor_view_id))
                        .padding(100, 100, 100, 100)
                        .show();
//Attach to Menu Item
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.sample_menu, menu);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                View view = findViewById(R.id.action_item2);

                ActionTooltip.anchorAt(MainActivity.this, view)
                        .padding(100, 100, 100, 100)
                        .setPositionTo(TooltipPosition.BOTTOM)
                        .setCustomView(R.layout.tooltip_view)
                        .show();
            }
        });
        return true;
    }
    
// Add Display Listener
  ActionTooltip.anchorAt(this, findViewById(R.id.hello_world))
          .padding(100, 100, 100, 100)
          .setPositionTo(TooltipPosition.TOP)
          .setForeverVisible(true)
          .setCustomView(customView)
          .setDisplayListener(new DisplayListener() {
              @Override
              public void onViewDisplayed(View view) {
                  Toast.makeText(
                          MainActivity.this, "I am displayed", Toast.LENGTH_SHORT)
                          .show();
              }

              @Override
              public void onViewHidden(View view) {
                  Toast.makeText(
                          MainActivity.this, "I am hidden", Toast.LENGTH_SHORT)
                          .show();
              }
          })
          .show();
          
//Set forever visible and hide on Action
    View customView = getLayoutInflater().inflate(R.layout.tooltip_view, null);

    final TooltipActionView tooltipActionView =
            ActionTooltip.anchorAt(this, findViewById(R.id.hello_world))
                    .padding(100, 100, 100, 100)
                    .setPositionTo(TooltipPosition.TOP)
                    .setForeverVisible(true)
                    .setCustomView(customView)
                    .show();

    customView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tooltipActionView.hide();
        }
    });
```

Setting custom view:


```java
View view = getLayoutInflater().inflate(R.layout.tooltip_view, null);
ActionTooltip.anchorAt(this, findViewById(R.id.hello_world))
            .padding(100, 100, 100, 100)
            .setPositionTo(TooltipPosition.TOP)
            .setCustomView(view)
            .show();
            
//or by layoutId
ActionTooltip.anchorAt(this, findViewById(R.id.hello_world))
            .padding(100, 100, 100, 100)
            .setPositionTo(TooltipPosition.TOP)
            .setCustomView(R.layout.tooltip_view)
            .show();
```

Changelog
-------------
v1.0.0
- `setPositionTo(TooltipPosition position)` Sets the Tooltip position in the anchor view. Values are: `TOP, BOTTOM, START, END`
- `setCustomView(View customView)` Sets a custom view. See Usage above
- `setTextAlignment(TooltipAlign align)` Aligns the Text in the Tooltip. Values are: `START, CENTER, END`
- `show()` Shows the Tooltip
- `setDuration(long duration)`
- `setBackgroundColor(@ColorInt int color)`
- `setDisplayListener(DisplayListener listener)` Sets a Listener which implements 2 methods: `onViewDisplayed(View view)` and `onViewHidden(View view)`
- `padding(@Px int left, @Px int top, @Px int right, @Px int bottom)`
- `setAnimation(FadeAnimation tooltipAnimation)`
- `setText(String text)` Sets text if you do not setCustomView.
- `setTextColor(int textColor)` Sets text color if you do not setCustomView.
- `setTextFont(Typeface typeface)` Sets text font if you do not setCustomView.
- `setTextSize(int unit, float textSize)` Sets text size if you do not setCustomView.
- `setTextGravity(int textGravity)` Sets text gravity if you do not setCustomView.
- `hideOnClick(boolean clickToHide)` It hides when you click the Tooltip
- `autoHide(boolean autoHide, long duration)` Auto hides after the duration set. The default duration is 4s.
- `setForeverVisible(boolean foreverVisible)` It makes Tooltip show all the time. You have to use the `hide()` so the Tooltip can be hidden.

v1.1.0
- renamed `padding(@Px int left, @Px int top, @Px int right, @Px int bottom)` to `setPadding(@Px int left, @Px int top, @Px int right, @Px int bottom)`
- renamed `setTextFont(Typeface typeface)` to `setTypeFace(Typeface typeface)`
- `setCustomView(@LayoutRes int layoutId)` Sets a custom view. See Usage above
- `setArrowHeightId(@IntegerRes int arrowHeightId)`
- `setArrowHeight(int arrowHeight)`
- `setAllCaps(boolean allCaps)`
- `setMaxLines(int maxLines)`
- `setMaxWidth(int maxPixels)`
- `setMinLines(int minLines)`
- `setMovementMethod(MovementMethod movementMethod)`
- `setTextColorId(@ColorRes int colorId)`
- `setTextColor(@ColorInt int color)`
- `setTextColor(@NonNull String colorString)`

v1.2.0
- `setBackgroundColorId(@ColorRes int colorId)`
- `setEllipsize(TextUtils.TruncateAt atWhere)`
- `setEms(int ems)`
- `setFilters(InputFilter[] filters)`
- `setError(CharSequence error)`
- `setHint(CharSequence hint)`
- `setHint(@StringRes int hintId)`
- `setHintTextColor(@ColorInt int color)`
- `setHorizontallyScrolling(boolean whether)`
- `setSingleLine(boolean singleLine)`
- `setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled)`
- `setElevation(float elevation)` Requires API 21 and above
- `setText(@StringRes int stringId)`


Useful methods
-------------

You can use the following methods:

- `setPositionTo(TooltipPosition position)` Sets the Tooltip position in the anchor view. Values are: `TOP, BOTTOM, START, END`
- `setCustomView(View customView)` Sets a custom view. See Usage above
- `setCustomView(@LayoutRes int layoutId)` Sets a custom view. See Usage above
- `setTextAlignment(TooltipAlign align)` Aligns the Text in the Tooltip. Values are: `START, CENTER, END`
- `show()` Shows the Tooltip
- `setDuration(long duration)`
- `setBackgroundColor(@ColorInt int color)`
- `setDisplayListener(DisplayListener listener)` Sets a Listener which implements 2 methods: `onViewDisplayed(View view)` and `onViewHidden(View view)`
- `setPadding(@Px int left, @Px int top, @Px int right, @Px int bottom)`
- `setAnimation(FadeAnimation tooltipAnimation)`
- `setText(String text)` Sets text if you do not setCustomView.
- `setTextColor(int textColor)` Sets text color if you do not setCustomView.
- `setTypeFace(Typeface typeface)` Sets text font if you do not setCustomView.
- `setTextSize(int unit, float textSize)` Sets text size if you do not setCustomView.
- `setTextGravity(int textGravity)` Sets text gravity if you do not setCustomView.
- `hideOnClick(boolean clickToHide)` It hides when you click the Tooltip
- `autoHide(boolean autoHide, long duration)` Auto hides after the duration set. The default duration is 4s.
- `setForeverVisible(boolean foreverVisible)` It makes Tooltip show all the time. You have to use the `hide()` so the Tooltip can be hidden.
- `setArrowHeightId(@IntegerRes int arrowHeightId)`
- `setArrowHeight(int arrowHeight)`
- `setAllCaps(boolean allCaps)`
- `setMaxLines(int maxLines)`
- `setMaxWidth(int maxPixels)`
- `setMinLines(int minLines)`
- `setMovementMethod(MovementMethod movementMethod)`
- `setTextColorId(@ColorRes int colorId)`
- `setTextColor(@ColorInt int color)`
- `setTextColor(@NonNull String colorString)`
- `setBackgroundColorId(@ColorRes int colorId)`
- `setEllipsize(TextUtils.TruncateAt atWhere)`
- `setEms(int ems)`
- `setFilters(InputFilter[] filters)`
- `setError(CharSequence error)`
- `setHint(CharSequence hint)`
- `setHint(@StringRes int hintId)`
- `setHintTextColor(@ColorInt int color)`
- `setHorizontallyScrolling(boolean whether)`
- `setSingleLine(boolean singleLine)`
- `setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled)`
- `setElevation(float elevation)` Requires API 21 and above
- `setText(@StringRes int stringId)`

License
-------

 Copyright 2017 Kostas Drakonakis

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
