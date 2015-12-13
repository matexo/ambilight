#include "FastLED.h"
#define NUM_LEDS 16

#define clockPinUpper 8
#define dataPinUpper 6
#define clockPinLower 2
#define dataPinLower 3

CRGB ledsUpper[NUM_LEDS];
CRGB ledsLower[NUM_LEDS];

void setup() {
  Serial.begin(9600);
  
  FastLED.addLeds<WS2801 , dataPinUpper , clockPinUpper , RGB>(ledsUpper, NUM_LEDS);
  FastLED.addLeds<WS2801 , dataPinLower , clockPinLower , RGB>(ledsLower, NUM_LEDS);
  FastLED.setBrightness(200);
}

void loop() {
  if(Serial.available() > 30) {
    if(Serial.read() == 0xff) {
      for(int i = 0; i<NUM_LEDS; i++) 
        {
        Serial.readBytes( (char*)(&ledsUpper[i]), 3);
        }
      for(int i = 0; i<NUM_LEDS; i++) 
        {
        Serial.readBytes( (char*)(&ledsLower[i]), 3); 
        }
      FastLED.show();
      delay(50);
    }
  }
}
