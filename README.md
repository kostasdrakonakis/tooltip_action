Action Tooltip [![Maven Central](https://img.shields.io/badge/Maven%20Central-spinner--preference-brightgreen.svg)](http://search.maven.org/#search%7Cga%7C1%7Ckostasdrakonakis) [ ![Download](https://api.bintray.com/packages/kdrakonakis/maven/tooltip-action/images/download.svg) ](https://bintray.com/kdrakonakis/maven/tooltip-action/_latestVersion)


A custom Tooltip that can be anchored to a view. By default it supports TextView only but you can setCustomView with your custom layout.xml file.


Download
--------

Download the latest JAR or grab via Maven:
```xml
<dependency>
  <groupId>com.github.kostasdrakonakis</groupId>
  <artifactId>tooltip-action</artifactId>
  <version>1.0.0</version>
</dependency>
```
or Gradle:
```groovy
compile 'com.github.kostasdrakonakis:tooltip-action:1.0.0'
```

Usage
-----

```java
ActionTooltip.anchorAt(this, findViewById(R.id.my_anchor_view_id))
                        .padding(100, 100, 100, 100)
                        .setCustomView(view).show();
```

Setting custom view:


```java
View view = getLayoutInflater().inflate(R.layout.tooltip_view, null);
ActionTooltip.anchorAt(this, findViewById(R.id.hello_world))
            .padding(100, 100, 100, 100)
            .setPositionTo(TooltipPosition.TOP)
            .setCustomView(view)
            .show();
```

Useful methods
-------------

You can use the following methods:

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
