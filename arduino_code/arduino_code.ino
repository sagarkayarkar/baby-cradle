#include <LiquidCrystal.h>
int val=0;
int ledPin = 5;
const int EN1 =  6;
const int RELAY = A3;
const int CRAD =  A2;    //for for cradle button
LiquidCrystal lcd(9,10,11,12,13,A0);
 
//Char used for reading in Serial characters
char state=0 ;
//*******************************************************************************************
 
void setup() {
  // initialise serial communications at 9600 bps:
  Serial.begin(9600);
  lcd.begin(20, 2); //change to 16, 2 for smaller 16x2 screens
 pinMode(ledPin, OUTPUT);
   pinMode(3, OUTPUT);
   pinMode(EN1, OUTPUT);
  pinMode(A5, INPUT );
    pinMode(A4, INPUT);
    Serial.begin(9600); 
 digitalWrite(ledPin, LOW);
  //digitalWrite(RELAY, LOW);
   // digitalWrite(EN1, LOW);
  lcd.setCursor(0,0);
   lcd.print(" MODERN CONTROLL ");
   lcd.setCursor(0,1);
   lcd.print(" BABY CRADLE  ");
        //  delay(4000);
           lcd.setCursor(0,0);

        }
 
void loop() {
 
  sendAndroidValues();
  //when serial values have been received this will be true
  if (Serial.available() > 0)
  {
   state = Serial.read();

   if (state == '1'){
  analogWrite(EN1, 255); 

   lcd.setCursor(0,1);
   lcd.print("    SWINGING   ");

 }
 else if (state == '0') {
  analogWrite(EN1, 0); 
    // digitalWrite(A3, LOW);
 
                lcd.setCursor(0,1);
         
            lcd.print(" CRADLE IS STOP ");

 
 }
 else if (state == '3') {

 lcd.setCursor(0,1);
         
            lcd.print("      SLOW SPEED    ");             
                 analogWrite(EN1, 155); 


 }
 else if (state == '4') {
 
  analogWrite(EN1, 105); 

               lcd.setCursor(0,1);
         
            lcd.print("   MEDIUM SPEED   ");  

 }

 
  else if (state == '7'){
 digitalWrite(3, HIGH);
   //analogWrite(EN1, 255); 

 
   lcd.setCursor(0,1);
         
            lcd.print("    FAN ON   "); 

 }
  else if (state == '8') {
    digitalWrite(3, LOW);
  

  }}
  //delay by 2s. Meaning we will be sent values every 2s approx
  //also means that it can take up to 2 seconds to change LED state
  delay(200);

}

 

//sends the values from the sensor over serial to BT module
void sendAndroidValues()
 {
  //puts # before the values so our app knows what to do with the data
  if (digitalRead(A5) == HIGH&&digitalRead(A4) == HIGH) {
 Serial.print('w');
   
 lcd.setCursor(1,0);
            lcd.print("   BABY URINATES   ");  
            digitalWrite(ledPin, HIGH);
         delay(1000);
 digitalWrite(ledPin, LOW);
         delay(1000);
                              
}
 else if (digitalRead(A5) == LOW&&digitalRead(A4) == LOW) {

   lcd.setCursor(1,0);
         
            lcd.print("CRADLE IS EMPTY ");  
   digitalWrite(ledPin, LOW);
   Serial.print('e');
 }
   else if (digitalRead(A5) == HIGH  && digitalRead(A4) == LOW ) {
      lcd.setCursor(0,0);
   lcd.print("                ");
   lcd.setCursor(0,1);
   lcd.print("                ");
 lcd.setCursor(1,0);
         
            lcd.print(" BABY IN CRADLE");          Serial.print('u');
     //    bluetooth();
   digitalWrite(ledPin, HIGH);
   }
        //added a delay to eliminate missed transmissions
}
 


