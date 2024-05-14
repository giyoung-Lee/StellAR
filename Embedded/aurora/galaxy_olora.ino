#define BLUE 11
#define GREEN 10
#define RED 9
#define WHITE 8
#define MOTER 6

void setup() {
  // put your setup code here, to run once:
  pinMode(RED, OUTPUT);
  pinMode(GREEN, OUTPUT);
  pinMode(BLUE, OUTPUT);
  pinMode(WHITE, OUTPUT);

  pinMode(MOTER,OUTPUT);
                                                                                  
  digitalWrite(RED, LOW);
  digitalWrite(GREEN, LOW);
  digitalWrite(BLUE, LOW);
  digitalWrite(WHITE, LOW);

}

void loop() {
  // put your main code here, to run repeatedly:

  analogWrite(MOTER,70);

  //레드 빼기 
  for (int i=0; i<255; i++){
    analogWrite(RED,i);
    // analogWrite(BLUE,-i);
    delay(40);
  }
  delay(3000);

  //레드 더하기
  for (int i=255; i>0; i--){
    analogWrite(RED,i);
    delay(40);
  }
  delay(3000);

  //블루 빼기 
  for (int i=0; i<255; i++){
    analogWrite( BLUE,i);
    delay(40);
  }
  delay(3000);

  //블루더하기 
  for (int i=255; i>0; i--){
    analogWrite(BLUE,i);
    delay(40);
  }
  delay(3000);


}
