# Т.к. основным инструментом для сборки Android-проектов является Gradle, 
# и по счастливому стечению обстоятельств есть официальный Docker-образ 
# мы решили за основу взять именно его с нужной нам версией Gradle
FROM gradle:5.4.1-jdk8

# Задаем переменные с локальной папкой для Android SDK и 
# версиями платформы и инструментария
ENV SDK_URL="https://dl.google.com/android/repository/sdk-tools-linux-3859397.zip" \
    ANDROID_HOME="/usr/local/android-sdk" \
    ANDROID_VERSION=28 \
    ANDROID_BUILD_TOOLS_VERSION=28.0.3

# Создаем папку, скачиваем туда SDK и распаковываем архив,
# который после сборки удаляем
RUN mkdir "$ANDROID_HOME" .android \
    && cd "$ANDROID_HOME" \
    && curl -o sdk.zip $SDK_URL \
    && unzip sdk.zip \
    && rm sdk.zip \
# В следующих строчках мы создаем папку и текстовые файлы 
# с лицензиями. На оф. сайте Android написано что мы 
# можем копировать эти файлы с машин где вручную эти 
# лицензии подтвердили и что автоматически 
# их сгенерировать нельзя
    && mkdir "$ANDROID_HOME/licenses" || true \
    && echo "24333f8a63b6825ea9c5514f83c2829b004d1" > "$ANDROID_HOME/licenses/android-sdk-license" \
    && echo "84831b9409646a918e30573bab4c9c91346d8" > "$ANDROID_HOME/licenses/android-sdk-preview-license"    

# Запускаем обновление SDK и установку build-tools, platform-tools
RUN $ANDROID_HOME/tools/bin/sdkmanager --update
RUN $ANDROID_HOME/tools/bin/sdkmanager "build-tools;${ANDROID_BUILD_TOOLS_VERSION}" \
    "platforms;android-${ANDROID_VERSION}" \
    "platform-tools"