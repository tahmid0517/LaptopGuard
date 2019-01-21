#include <windows.h>
#include <stdio.h>

int main() 
{
  SYSTEM_POWER_STATUS status;
  GetSystemPowerStatus( &status );
  int isConnectedToLine = status.ACLineStatus;
  printf("%d",isConnectedToLine);
  return 0;
}
