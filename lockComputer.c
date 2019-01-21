#include <windows.h>

#pragma comment(lib, "user32.lib")

int wmain(void)
{
  LockWorkStation();
  return 0;
}
