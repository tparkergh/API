// CWDS Key Generator Library.
// See KeyJNI.java for usage.


//===============================
// BUILD INSTRUCTIONS:
//===============================

// COMPILE/LINK FOR JNI ON OS X:
// 1: JDK location: 
//     export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/
// 2: Compile Java class:
//     javac gov/ca/cwds/rest/util/jni/KeyJNI.java
// 3: Generate C header:
//     javah -jni gov.ca.cwds.rest.util.jni.KeyJNI
// 4: Compile C object:
//     CLANG:
//        clang++ -o KeyJNI.o -c cws_randgen.cpp -dead_strip -O2 -DCWDS_BUILD_DLL -DNDEBUG -stdlib=libc++ -fpermissive -w -Wfatal-errors -I. -I..  -I../.. -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin -std=gnu++14
// 5: Make shared library:
//     CLANG:
//        clang++ -dynamiclib -o libKeyJNI.dylib KeyJNI.o

//===============================
// EXECUTE JAVA:
//===============================

// java -ea -Dcwds.jni.force=Y -Djava.library.path=.:lib/ gov.ca.cwds.rest.util.jni.KeyCmdLine -d OpHh4Kr0X5

//===============================
// SHOW DEPENDENCIES ON OS X:
//===============================

// otool -L libKeyJNI.dylib
//     libKeyJNI.dylib (compatibility version 0.0.0, current version 0.0.0)
//     /usr/lib/libc++.1.dylib (compatibility version 1.0.0, current version 120.1.0)
//     /usr/lib/libSystem.B.dylib (compatibility version 1.0.0, current version 1226.10.1)

//===============================
// BUILD STANDALONE EXECUTABLE:
//===============================

// OS X:
// clang++ cws_randgen.cpp -o cws_randgen -pipe -march=native -dead_strip -O2 -DNDEBUG -fexpensive-optimizations -stdlib=libc++ -fpermissive -fomit-frame-pointer -w -Wfatal-errors -I. -I.. -I../.. -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin -std=gnu++14;strip -S -x cws_randgen;upx -v --best cws_randgen

// MAC JAVA HOME: /Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/

//===============================
// BUILD ON LINUX:
//===============================

// readlink -f $(which java)
// On Ubuntu: JAVA_HOME=/usr/lib/jvm/java-8-oracle
//
// LINUX (UBUNTU) GCC: static, stand-alone executable:
// g++ cws_randgen.cpp -o cws_randgen -pipe -s -O2 -static -DNDEBUG -fexpensive-optimizations -fpermissive -fomit-frame-pointer -w -Wfatal-errors -I. -I.. -I../..  -I${JAVA_HOME}/include -L/usr/lib/x86_64-linux-gnu/ -std=gnu++14;upx -v cws_randgen

// LINUX (UBUNTU) GCC: shared lib:
// COMPILE:
// g++ -c cws_randgen.cpp -o cws_randgen.o -O2 -fPIC -DCWDS_BUILD_DLL -DNDEBUG -fpermissive -w -Wfatal-errors -I. -I.. -I../.. -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux -static-libstdc++ -L/usr/lib/x86_64-linux-gnu/ -std=gnu++14

// LINK:
// g++ -fPIC -o libKeyJNI.so -shared cws_randgen.o -lc -I. -I.. -I../.. -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux -L/usr/lib/x86_64-linux-gnu/ -static-libstdc++ -std=gnu++14

// RUN:
// java -Djava.library.path=. gov.ca.cwds.rest.util.jni.KeyJNI


//===============================
// DIAGNOSTICS:
//===============================

//
// List exported functions in shared library: nm libKeyJNI.so | grep Java
//

//===============================
// JNI REFERENCE:
//===============================

// https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaNativeInterface.html




/**
 ______________________________________________________________________________
|                                                                              |
|                         CHILD WELFARE MISSILE COMMAND                        |
|______________________________________________________________________________|
      \                                  /                      /
       \     .                          /                      /            x
        \                              /                      /
         \                            /          +           /
          \            +             /                      /
           *                        /                      /
                                   /      .               /
    X                             /                      /            X
                                 /                     ###
                                /                     # % #
                               /                       ###
                      .       /
     .                       /      .            *           .
                            /
                           *
                  +                       *

                                       ^
####      __     __     __          #######         __     __     __        ####
####    /    \ /    \ /    \      ###########     /    \ /    \ /    \      ####
################################################################################
################################################################################
# WAVE 11 ######## SCORE 231037 ################################ HIGH FFFFFFFF #
################################################################################
*/



//-----------------------------------------------------------------------------
//  Filename:       cws_randgen.cpp
//-----------------------------------------------------------------------------

// Include files
#define __STDC_WANT_LIB_EXT1__ 1
#define __STDC_WANT_SECURE_LIB__ 1
#include <ctype.h>
#include <string.h>
#include <math.h>
#include <sys/timeb.h>

#include "cws_common.hpp"

#include <cstring>
#include <cstdio>
#include <getopt.h>
#include <cstdint>

#ifdef CWDS_BUILD_DLL
// #include "KeyJNI.h"
#include "gov_ca_cwds_rest_util_jni_KeyJNI.h"
#endif



// To generate an identifier, the current date/timestamp is rearranged as shown below, placing
// less-significant time units into more-significant fields. This convolution provides better
// "hashing" into cache and the database.
//
//   ¦   ~8 bits     ¦   6 bits  ¦   6 bits  ¦ 5 bits  ¦ 5 bits  ¦4 bits ¦    8 bits     ¦
//   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//   ¦  hundredths   ¦  seconds  ¦  minutes  ¦  hours  ¦   day   ¦month-1¦  year - 1900  ¦
//   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
// bytes ¦       :       ¦       :       ¦       :       ¦       :       ¦       :       ¦
//
// As shown above, certain bit values would produce unrealistic dates/times. For example,
// month values of 0000-1011 represent January-December, but the field has room for
// unrealistic months 1100-1111. Similarly unrealistic values could be placed into other
// fields. The first and last fields are of special interest.
//
//   hundredths:  Although shown as an 8-bit field here, not all unrealistic values are usable,
//                since the overall number must fit into seven base-62 characters. That limits
//                the hundredths field to values from 0 to 204.
//
//   year:        This algorithm supports years from 1900 to 2155.
//
// Note that CWS/CMS has made use of unrealistic values on some occasions. For example, the
// project may need to generate many identifiers during a minimum outage window. In order to
// make all of those generated identifiers correspond to the date/hour range of the outage,
// project-generated identifiers may coerce unrealistic values for minutes, seconds, and hundredths.
// This explains why some identifiers may translate to timestamps with 7 digits (rather than
// the usual 6) after the final decimal point -- It may be showing more than 99 "hundredths"!
// This also means that the longest formatted date/timestamp output is 27 characters (including
// punctuation), although it will usually fit in 26.
//
// Once packed into the bit arrangement shown above, the number is converted to seven base-62
// characters, using first the digits 0-9, then uppercase letters, then lowercase letters.
//
// The final 3 characters of the identifier indicate the staffperson (or project process) which
// created the row.
//
// For the User Interface, the identifier can also be converted into a formatted 19-digit decimal
// number. In the 19-digit format, the first 13 decimal digits are a conversion of the first
// seven base-62 characters, while the last 6 decimal digits are an independent conversion of
// the last 3 base-62 characters (ie, staffperson ID) from the identifier. The 19 digits are
// formatted into three groups of four digits, followed by a final group of seven digits, so the
// full string length is 22 characters with punctuation.
//
// In this source file, the 3 formats are referred to as:
//   Key:            10 base-62 characters (0-9, A-Z, a-z):               tttttttppp
//   UI Identifier:  19 decimal digits (22 characters with punctuation):  tttt-tttt-tttt-tpppppp
//   Timestamp:      26 characters in DB2 format:                         YYYY-MM-DD-hh.mm.ss.xx0000
//         or rarely 27 (as explained above):                             YYYY-MM-DD-hh.mm.ss.xxx0000
// where:
//   All timestamp fields include leading zeros.
//   t... represents convolved time
//   p... represents the staffperson
//   YYYY represents the year
//   MM   represents the month
//   DD   represents the day of the month
//   hh   represents the hour (00 to 23)
//   mm   represents minutes
//   ss   represents seconds
//   x... represents hundredths of seconds


constexpr const std::size_t BASE_62_SIZE {62};

//==============================
// WIN32/MFC TYPE PORT:
//==============================

// DRS: Look at all this Windows gooberness I had to port over.
// I should get a medal. 

#ifndef BASETYPES
#define BASETYPES
typedef unsigned long ULONG;
typedef ULONG *PULONG;
typedef unsigned short USHORT;
typedef USHORT *PUSHORT;
typedef unsigned char UCHAR;
typedef UCHAR *PUCHAR;
#endif  /* !BASETYPES */

#define MAX_PATH        260

#ifndef NULL
#ifdef __cplusplus
#define NULL    0
#else
#define NULL    ((void *)0)
#endif
#endif

#ifndef FALSE
#define FALSE    0
#endif

#ifndef TRUE
#define TRUE    1
#endif

#ifndef IN
#define IN
#endif

#ifndef OUT
#define OUT
#endif

#ifndef OPTIONAL
#define OPTIONAL
#endif

#define WINAPI __stdcall

typedef wchar_t WCHAR;

#ifdef UNICODE
 typedef WCHAR TCHAR;
#else
 typedef char TCHAR;
#endif

#ifndef CONST
#define CONST const
#endif

typedef CONST WCHAR *LPCWSTR, *PCWSTR;


// AFXAPI is used on global public functions
#ifndef AFXAPI
    #define AFXAPI __stdcall
#endif


#undef far
#undef near
#undef pascal

#define far
#define near

#if (!defined(_MAC)) && ((_MSC_VER >= 800) || defined(_STDCALL_SUPPORTED))
#define pascal __stdcall
#else
#define pascal
#endif

#if defined(DOSWIN32) || defined(_MAC)
#define cdecl _cdecl
#ifndef CDECL
#define CDECL _cdecl
#endif
#else
#define cdecl
#ifndef CDECL
#define CDECL
#endif
#endif

#ifdef _MAC
#define CALLBACK    PASCAL
#define WINAPI      CDECL
#define WINAPIV     CDECL
#define APIENTRY    WINAPI
#define APIPRIVATE  CDECL
#ifdef _68K_
#define PASCAL      __pascal
#else
#define PASCAL
#endif
#elif (_MSC_VER >= 800) || defined(_STDCALL_SUPPORTED)
#define CALLBACK    __stdcall
#define WINAPI      __stdcall
#define WINAPIV     __cdecl
#define APIENTRY    WINAPI
#define APIPRIVATE  __stdcall
#define PASCAL      __stdcall
#else
#define CALLBACK
#define WINAPI
#define WINAPIV
#define APIENTRY        WINAPI
#define APIPRIVATE
#define PASCAL          pascal
#endif

#ifndef _M_CEE_PURE
#ifndef WINAPI_INLINE
#define WINAPI_INLINE   WINAPI
#endif
#endif

#define _export

#undef FAR
#undef  NEAR
#define FAR             far
#define NEAR            near
#ifndef CONST
#define CONST           const
#endif


typedef unsigned long               DWORD;
typedef int                          BOOL;
typedef unsigned char                BYTE;
typedef unsigned short               WORD;
typedef float                       FLOAT;
typedef FLOAT                     *PFLOAT;
typedef BOOL near                  *PBOOL;
typedef BOOL far                  *LPBOOL;
typedef BYTE near                  *PBYTE;
typedef BYTE far                  *LPBYTE;
typedef int near                    *PINT;
typedef int far                    *LPINT;
typedef WORD near                  *PWORD;
typedef WORD far                  *LPWORD;
typedef long far                  *LPLONG;
typedef DWORD near                *PDWORD;
typedef DWORD far                *LPDWORD;
typedef void far                  *LPVOID;
typedef CONST void far           *LPCVOID;

typedef int                           INT;
typedef unsigned int                 UINT;
typedef unsigned int               *PUINT;

typedef __int64_t INT_PTR,      *PINT_PTR;
typedef __int64_t UINT_PTR,    *PUINT_PTR;
typedef __int64_t LONG_PTR,    *PLONG_PTR;
typedef __int64_t ULONG_PTR,  *PULONG_PTR;


typedef char  CHAR;
typedef short SHORT;
typedef long  LONG;

typedef CHAR *PCHAR, *LPCH, *PCH;
typedef CONST CHAR *LPCCH, *PCCH;

typedef CHAR *NPSTR, *LPSTR, *PSTR;
typedef PSTR *PZPSTR;
typedef CONST PSTR *PCZPSTR;
typedef CONST CHAR *LPCSTR, *PCSTR;
typedef PCSTR *PZPCSTR;
typedef CONST PCSTR *PCZPCSTR;

//==============================
// WIN32/MFC DATETIME PORT:
//==============================

typedef struct _SYSTEMTIME {
  WORD wYear;
  WORD wMonth;
  WORD wDayOfWeek;
  WORD wDay;
  WORD wHour;
  WORD wMinute;
  WORD wSecond;
  WORD wMilliseconds;
} SYSTEMTIME, *PSYSTEMTIME, *LPSYSTEMTIME;

// Constants for days and weeks.
typedef std::chrono::duration<int, std::ratio<60*60*24>> days;
using weeks = std::chrono::duration<long, std::ratio_multiply<days::period, std::ratio<7>>>;

//==============================
// WIN32/MFC FUNCTION PORT:
//==============================

#ifndef _WIN32
void Yield() {
	using namespace std;
    this_thread::yield();
    this_thread::sleep_for(chrono::milliseconds(3));
}
#endif

// Low-level sanity checks for memory blocks.
// DRS: in our case, sanity has never been a primary concern. Just return true. :-)
inline BOOL AfxIsValidAddress(const void* lp, UINT_PTR nBytes, BOOL bReadWrite = TRUE) {
    return true;
}

inline BOOL AfxIsValidString(LPCWSTR lpsz, int nLength = -1) {
    return true;
}

inline BOOL AfxIsValidString(LPCSTR lpsz, int nLength = -1) {
    return true;
}

//==============================
// GLOBALS:
//==============================

static SYSTEMTIME * g_win_tm = nullptr;

//==============================
// GETLOCALTIME:
//==============================

SYSTEMTIME std_time_to_win_time(const std::tm & a_tm, const int millis) {
    SYSTEMTIME retval;
    
    retval.wYear         = 1900 + a_tm.tm_year;
    retval.wMonth        = 1 + a_tm.tm_mon;
    retval.wDayOfWeek    = a_tm.tm_wday;  // DRS: not used in this unit.
    retval.wDay          = a_tm.tm_mday;
    retval.wHour         = a_tm.tm_hour;
    retval.wMinute       = a_tm.tm_min;
    retval.wSecond       = a_tm.tm_sec;
    retval.wMilliseconds = millis;
    
    return retval;
}

// Option: provide a start date/time for testing.
void GetLocalTime (SYSTEMTIME * win_tm) {
    using namespace std;
    using namespace std::chrono;

    if ( g_win_tm == nullptr ) {

        // LIVE MODE: get current datetime.
        const auto tp  = system_clock::now();
        const auto tse = tp.time_since_epoch();
        const auto tt  = system_clock::to_time_t(tp);
        const tm *ltm  = localtime(&tt);

        win_tm->wYear         = 1900 + ltm->tm_year;
        win_tm->wMonth        = 1 + ltm->tm_mon;
        win_tm->wDayOfWeek    = ltm->tm_wday;           // DRS: not used in this unit.
        win_tm->wDay          = ltm->tm_mday;
        win_tm->wHour         = ltm->tm_hour;
        win_tm->wMinute       = ltm->tm_min;
        win_tm->wSecond       = ltm->tm_sec;
        win_tm->wMilliseconds = duration_cast<milliseconds>(tse).count() % 1000;
        
    } else {

        // TEST MODE: pass in fixed datetime.
        win_tm->wYear         = g_win_tm->wYear;
        win_tm->wMonth        = g_win_tm->wMonth;
        win_tm->wDayOfWeek    = g_win_tm->wDayOfWeek;   // DRS: not used in this unit.
        win_tm->wDay          = g_win_tm->wDay;
        win_tm->wHour         = g_win_tm->wHour;
        win_tm->wMinute       = g_win_tm->wMinute;
        win_tm->wSecond       = g_win_tm->wSecond;
        win_tm->wMilliseconds = g_win_tm->wMilliseconds;
        
    }
}

inline void printWinTime(std::ostream & os, const SYSTEMTIME & win_tm) {
    using namespace std;
    cout << "\nyear = "   << win_tm.wYear
         << ", month = "  << win_tm.wMonth
         << ", day = "    << win_tm.wDay
         << ", hour = "   << win_tm.wHour
         << ", minute = " << win_tm.wMinute
         << ", second = " << win_tm.wSecond
         << ", millis = " << win_tm.wMilliseconds;
}

//==============================
// RANDGEN CONSTANTS:
//==============================

constexpr const int nSZ_KEY           = 10;
constexpr const int nSZ_KEYSTAFFID    =  3;
constexpr const int nSZ_KEYTIMESTAMP  =  7;
constexpr const int nSZ_UISTAFFID     =  6;
constexpr const int nSZ_UITIMESTAMP   = 26;
constexpr const int nSZ_UIIDENTIFIER  = 22;
constexpr const int nSZ_PTIMESTAMP    = 11;

constexpr const int nSZ_USERID        =  8;

constexpr const int nSZ_POWVEC        = 19;
constexpr const int nMAX_BASE         = BASE_62_SIZE;
constexpr const int nDEFAULT_BASE     = BASE_62_SIZE;

constexpr const int nSZ_UIIDSTAFFID   =  6; // for converting a key to a UI identifier
constexpr const int nSZ_UIIDTIMESTAMP = 13;

constexpr const double nSHIFT_HSECOND = (double)(1L << 34);  // 34 bit shift (2 to the 34th power)
constexpr const double nSHIFT_SECOND  = (double)(1L << 28);  // 28 bit shift (2 to the 28th power)
constexpr const double nSHIFT_MINUTE  = (double)(1L << 22);  // 22 bit shift (2 to the 22nd power)
constexpr const double nSHIFT_HOUR    = (double)(1L << 17);  // 17 bit shift (2 to the 17th power)
constexpr const double nSHIFT_DAY     = (double)(1L << 12);  // 12 bit shift (2 to the 12th power)
constexpr const double nSHIFT_MONTH   = (double)(1L <<  8);  //  8 bit shift (2 to the  8th power)
constexpr const double nSHIFT_YEAR    = (double)(1L <<  0);  //  0 bit shift (2 to the  0th power)

//==============================
// STATIC DATA
//==============================

static const double anPowVec10[nSZ_POWVEC] = {
    1.000000000000000e+000, 1.000000000000000e+001,
    1.000000000000000e+002, 1.000000000000000e+003,
    1.000000000000000e+004, 1.000000000000000e+005,
    1.000000000000000e+006, 1.000000000000000e+007,
    1.000000000000000e+008, 1.000000000000000e+009,
    1.000000000000000e+010, 1.000000000000000e+011,
    1.000000000000000e+012, 1.000000000000000e+013,
    1.000000000000000e+014, 1.000000000000000e+015,
    1.000000000000000e+016, 1.000000000000000e+017,
    1.000000000000000e+018
};

static const double anPowVec62[nSZ_POWVEC] = {
    1.000000000000000e+000, 6.200000000000000e+001,
    3.844000000000000e+003, 2.383280000000000e+005,
    1.477633600000000e+007, 9.161328320000000e+008,
    5.680023558400000e+010, 3.521614606208000e+012,
    2.183401055848960e+014, 1.353708654626355e+016,
    8.392993658683402e+017, 5.203656068383710e+019,
    3.226266762397900e+021, 2.000285392686698e+023,
    1.240176943465753e+025, 7.689097049487666e+026,
    4.767240170682353e+028, 2.955688905823059e+030,
    1.832527121610297e+032
};

static const char acConvTbl[] = {
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
    'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
    'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
    'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
    'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
    'y', 'z'
};

//==============================
// FUNCTION PROTOTYPES:
//==============================

static void   BaseConvert        (char *szDstStr, int nDstBase, int nDstWidth, const char *szSrcStr, int nSrcBase);
static char * CreateTimestampStr (char *szTimestampStr);
static void   DoubleToTimestamp  (double nTimestamp, struct tm *phNow, int *pnHSeconds);
static char * DoubleToStrN       (char *szDstStr, int nDstStrWidth, double nSrcVal, double *pnPowVec);
static char * Itoa               (int nIn, char *szOut, int nOutputSize);
static double StrToDouble        (const char *szSrcStr, int nSrcBase, double *pnPowVec);

// DRS: linker error on some platforms: lstrcpynA redefinition.
// inline static char * StrCpyN (char *szDst, const char *szSrc, int nLen);

static double TimestampToDouble(LPSYSTEMTIME lpTime);

//==============================
// DLL PUBLIC FUNCTIONS:
//==============================

void WINAPI _export ckMakeNewKey          (const char *szUIStaffId, char *szKey);
void WINAPI _export MakeTimestampStr      (char *szUITimestamp);
void WINAPI _export NewKeyFromStaffId     (const char *szStaffId, char *szKey);
void WINAPI _export MakeKeyAndTimeStamp   (const char *szStaffId, char *szKey, char *szTimeStamp);
void WINAPI _export NewKeyFromUIStaffId   (const char *szUIStaffId, char *szKey);
void WINAPI _export GetStaffIdFromKey     (const char *szKey, char *szStaffId);
void WINAPI _export GetUIStaffIdFromKey   (const char *szKey, char *szUIStaffId);
void WINAPI _export GetUIIdentifierFromKey(const char *szKey, char *szUIIdentifier);
void WINAPI _export GetKeyFromUIIdentifier(const char *szUIIdentifier, char *szKey);
void WINAPI _export GetUITimestampFromKey (const char *szKey, char *szUITimestamp);
void WINAPI _export GetPTimeStampFromKey  (const char *szKey, char *szPTimestamp);

//==============================
// TRACE/ASSERT:
//==============================

// Convenient trace methods.

typedef int (*printf_alias)(const char*, ...);
printf_alias Trace = std::printf;
printf_alias TRACE = std::printf;

#define sprintf_s std::snprintf

// DRS: not included from string.h in Clang/LLVM??
// errno_t strcat_s(char *restrict dest, rsize_t destsz, const char *restrict src);
// strcat_s(const_cast<char*>(CreateTimestampStr(szKey)), nSZ_KEY + 1, szStaffId);

#ifdef __linux__
typedef size_t rsize_t;
#endif

void strcat_s(char * dest, rsize_t destsz, const char * src) {
    std::strncat(dest, src, destsz);
}

void AssertTrace (BOOL bExp, LPCSTR pszFormat, ...) {
    if ( !bExp ) {
        va_list arglist;
        va_start( arglist, pszFormat );
        vprintf( pszFormat, arglist );
        va_end( arglist ); // must release the vargs.
    }
}

inline bool is_base62(char c) {
    return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
}


//-----------------------------------------------------------------------------
//  Function:     StrCpyN
//
//  Description:  Copies 'n' bytes from the source string to the destination
//                string. The destination string is then null terminated.
//
//  Inputs:       szDst - destination string
//                szSrc - source string
//                nLen - number of bytes to copy
//
//  Outputs:
//                RETURNS - a pointer to the destination string.
//----------------------------------------------------------------------------- 

// DRS: Visual C++ linker error: lstrcpnA redefinition in Win64. Ok in Win32.
// OK in clang/gcc.
inline static char * StrCpyN (char *szDst, const char *szSrc, int nLen) {
    memcpy (szDst, szSrc, nLen);  // copy
    szDst[nLen] = '\0';           // terminate
    return szDst;
}

//-----------------------------------------------------------------------------
//                              Exported Functions
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
//  Function:     ckMakeNewKey
//
//  Description:  THIS FUNCTION IS HERE FOR COMPATIBILITY REASONS ONLY!!!
//                It has been replaced by NewKeyFromUIStaffId (which this
//                function calls). It will be removed in the future.
//
//  Inputs:       szUIStaffId - a base-10 staff person id.
//
//  Outputs:      szKey - a base-62 key
//                        An empty string on an error.
//                RETURNS - <none>
//-----------------------------------------------------------------------------
void WINAPI _export ckMakeNewKey(const char *szUIStaffId, char *szKey) {
    Trace("ckMakeNewKey() is obsolete. Use NewKeyFromStaffId() instead.\n");
    NewKeyFromUIStaffId(szUIStaffId, szKey);
}

//-----------------------------------------------------------------------------
//  Function:     MakeTimestampStr
//
//  Description:  This function was added to provide a DOS style timestamp
//                with granularity down to 1/100 of a second.
//
//  Inputs:       szUITimestamp  - blank 26 byte string
//
//  Outputs:      szUITimestamp  - 26 byte timestamp
//                        An empty string on an error.
//                RETURNS - <none>
//-----------------------------------------------------------------------------
void WINAPI _export MakeTimestampStr(char *szUITimestamp) {
    char szStaffId[6] = "123  ";
    char szKey[10] = "xxx";

    // Make a key using a dummy staff Id
    NewKeyFromUIStaffId(szStaffId, szKey);

    // Use the Key to generate the timestamp
    GetUITimestampFromKey(szKey, szUITimestamp);
}

//-----------------------------------------------------------------------------
//  Function:     NewKeyFromStaffId
//
//  Description:  Generates a new unique key using the staff person
//                identifier as part of the key.
//
//  Inputs:       szStaffId - a base-62 staff person id.
//
//  Outputs:      szKey - a base-62 key
//                        An empty string on an error.
//                RETURNS - <none>
//-----------------------------------------------------------------------------
void WINAPI _export NewKeyFromStaffId(const char *szStaffId, char *szKey) {
    using namespace std;

    try {
        AssertTrace(strlen(szStaffId) == nSZ_KEYSTAFFID,
            "\n'%s' has an invalid staff-person identifier string length.\n", szStaffId);
        AssertTrace(strcmp(szStaffId, "000"), "\n'%s' is an invalid staff person identifier.\n", szStaffId);
        AssertTrace(AfxIsValidAddress(szKey, nSZ_KEY + 1), "\nInvalid address specified for szKey.\n");

        // Make the timestamp and add the staff person id to the end.
        strcat_s(const_cast<char*>(CreateTimestampStr(szKey)), nSZ_KEY + 1, szStaffId);
    } catch (std::exception e) {
        cerr << "***** CAUGHT EXCEPTION! ***** : " << e.what() << std::endl;
        szKey[0] = '\0';
    }
}

//-----------------------------------------------------------------------------
//  Function: MakeKeyAndTimeStamp
//
//  Description:  Generates a new unique key using the staff person id
//                and a timestamp as part of the key.
//
//  Inputs:       szStaffId    - a three character staff id
//
//  Outputs:      szKey        - a 10 character base-62 key
//                szTimeStamp  - an 11 character Timestamp (hh:mm:ss:dsds)
//                               An empty string on an error.
//                RETURNS - <none>
//-----------------------------------------------------------------------------
void WINAPI _export MakeKeyAndTimeStamp(const char *szStaffId, char *szKey, char *szTimeStamp) {
    // Make the key
    NewKeyFromStaffId(szStaffId, szKey);

    // Use the Key to generate the timestamp
    GetPTimeStampFromKey(szKey, szTimeStamp);
    szKey[10] = (char)0;
    szTimeStamp[11] = (char)0;
}

//-----------------------------------------------------------------------------
//  Function:     NewKeyFromUIStaffId
//
//  Description:  Generates a new unique key using the UI displayable
//                version of the staff person identifier as part of the key.
//
//  Inputs:       szUIStaffId - a base-10 staff person id.
//
//  Outputs:      szKey - a base-62 key
//                        An empty string on an error.
//                RETURNS - <none>
//-----------------------------------------------------------------------------
void WINAPI _export NewKeyFromUIStaffId(const char *szUIStaffId, char *szKey) {
    char szKeyStaffId[nSZ_KEYSTAFFID + 1];

    try {
        AssertTrace(strlen(szUIStaffId) <= nSZ_UISTAFFID,
        "'%s' has an invalid staff-person identifier string length.", szUIStaffId);
        AssertTrace(AfxIsValidAddress(szKey, nSZ_KEY + 1), "Invalid address specified for szKey.");

        // Convert the staff person id to base 62.
        BaseConvert(szKeyStaffId, nDEFAULT_BASE, nSZ_KEYSTAFFID, szUIStaffId, 10);

        // Make the timestamp and add the staff person id to the end.
        strcat_s(CreateTimestampStr(szKey), nSZ_KEY + 1, szKeyStaffId);
    } catch (std::exception e) {
        std::cerr << "***** CAUGHT EXCEPTION! ***** : " << e.what() << std::endl;
        szKey[0] = '\0';
    }
}

//-----------------------------------------------------------------------------
//  Function:     GetStaffIdFromKey
//
//  Description:  Obtains the staff person identifier from the generated key.
//
//  Inputs:       szKey - the previously generated key.
//
//  Outputs:      szStaffId - the staff person id (in base 62)
//                            An empty string on an error.
//                RETURNS - <none>
//-----------------------------------------------------------------------------
void WINAPI _export GetStaffIdFromKey(const char *szKey, char *szStaffId) {
    using namespace std;
    try {
        AssertTrace(strlen(szKey) == nSZ_KEY, "'%s' has an invalid key string length.", szKey);
        AssertTrace(AfxIsValidAddress(szStaffId, nSZ_KEYSTAFFID + 1), "Invalid address specified for szStaffId.");

        // Copy out the staff person id.
        StrCpyN(szStaffId, szKey + nSZ_KEYTIMESTAMP, nSZ_KEYSTAFFID);
    } catch (std::exception e) {
        cerr << "***** CAUGHT EXCEPTION! ***** : " << e.what() << std::endl;
        szStaffId[0] = '\0';
    }
}

//-----------------------------------------------------------------------------
//  Function:     GetUIStaffIdFromKey
//
//  Description:  Obtains a UI displayable staff person identifier from
//                the generated key.
//
//  Inputs:       szKey - the previously generated key.
//
//  Outputs:      szUIStaffId - the staff person id (in base 10)
//                              An empty string on an error.
//                RETURNS - <none>
//-----------------------------------------------------------------------------
void WINAPI _export GetUIStaffIdFromKey(const char *szKey, char *szUIStaffId) {
    using namespace std;
    try {
        AssertTrace(strlen(szKey) == nSZ_KEY, "'%s' has an invalid key string length.", szKey);
        AssertTrace(AfxIsValidAddress(szUIStaffId, nSZ_UISTAFFID + 1), "Invalid address specified for szUIStaffId.");

        // Convert the staff person id of the key to the UI display (base 10).
        BaseConvert(szUIStaffId, 10, nSZ_UISTAFFID, szKey + nSZ_KEYTIMESTAMP, nDEFAULT_BASE);
    } catch (std::exception e) {
        cerr << "***** CAUGHT EXCEPTION! ***** : " << e.what() << std::endl;
        szUIStaffId[0] = '\0';
    }
}

//-----------------------------------------------------------------------------
// Function:      GetUIIdentifierFromKey
//
// Description:   Obtains UI displayable identifier from the generated key.
//
// Inputs:        szKey - the previously generated key.
//
// Outputs:       szUIIdentifier - the identifier (in base 10)
//                                 An empty string on an error.
//                RETURNS - <none>
//-----------------------------------------------------------------------------
void WINAPI _export GetUIIdentifierFromKey(const char *szKey, char *szUIIdentifier) {
    using namespace std;
    char szTimestamp[nSZ_KEYTIMESTAMP + 1];
    char szStaffId[nSZ_KEYSTAFFID + 1];
    string sConvertKeyToUI;

    try {
        AssertTrace(strlen(szKey) == nSZ_KEY, "'%s' has an invalid key string length.", szKey);
        AssertTrace(AfxIsValidAddress(szUIIdentifier, nSZ_UIIDENTIFIER + 1), "Invalid address specified for szUIIdentifier.");

        StrCpyN(szTimestamp, szKey, nSZ_KEYTIMESTAMP);
        StrCpyN(szStaffId, szKey + nSZ_KEYTIMESTAMP, nSZ_KEYSTAFFID);

        // convert the entire key to a displayable string (base 10).
        BaseConvert(szUIIdentifier, 10, nSZ_UIIDTIMESTAMP, szTimestamp, nDEFAULT_BASE);
        BaseConvert(szUIIdentifier + nSZ_UIIDTIMESTAMP, 10, nSZ_UIIDSTAFFID, szStaffId, nDEFAULT_BASE);

        sConvertKeyToUI = szUIIdentifier;  // convert to std::string
        sConvertKeyToUI = sConvertKeyToUI.substr(0,4) + "-" + sConvertKeyToUI.substr(4, 4) + "-"
        + sConvertKeyToUI.substr(8, 4) + "-" + sConvertKeyToUI.substr(12);  // insert 3 dashes every 4th character
        StrCpyN(szUIIdentifier, sConvertKeyToUI.c_str(), nSZ_UIIDENTIFIER);
    } catch (std::exception e) {
        cerr << "***** CAUGHT EXCEPTION! ***** : " << e.what() << std::endl;
        szUIIdentifier[0] = '\0';
    }
}

//-----------------------------------------------------------------------------
// Function:      GetKeyFromUIIdentifier
//
// Description:   Obtains base 62 key from the supplied UI displayable identifier.
//
// Inputs:        szUIIdentifier - the identifier (in base 10)
//
// Outputs:       szKey - the previously generated key.
//                        An empty string on an error.
//                RETURNS - <none>
//-----------------------------------------------------------------------------
void WINAPI _export GetKeyFromUIIdentifier(const char *szUIIdentifier, char *szKey) {
	using namespace std;
    string sConvertUIToKey;
    string sTempKey;
    char szTimestamp[nSZ_UIIDTIMESTAMP + 1];
    char szStaffId[nSZ_UIIDSTAFFID + 1];
    char szTempKey[nSZ_UIIDTIMESTAMP + nSZ_UIIDSTAFFID + 1];
    char szKeyStaffId[nSZ_KEYSTAFFID + 1];

    try {
        AssertTrace(strlen(szUIIdentifier) == nSZ_UIIDENTIFIER, "'%s' has an invalid UI identifier string length.", szUIIdentifier);
        AssertTrace(AfxIsValidAddress(szKey, nSZ_KEY + 1), "Invalid address specified for szKey.");

        sConvertUIToKey = szUIIdentifier;  // convert to std::string
        sTempKey = sConvertUIToKey.substr(0,4) + sConvertUIToKey.substr(5, 4)
        + sConvertUIToKey.substr(10, 4) + sConvertUIToKey.substr(15);  // removes 3 dashes every 4th character

        StrCpyN(szTempKey,   sTempKey.c_str(),   nSZ_UIIDTIMESTAMP + nSZ_UIIDSTAFFID + 1);
        StrCpyN(szTimestamp, szTempKey,  nSZ_UIIDTIMESTAMP);
        StrCpyN(szStaffId,   szTempKey + nSZ_UIIDTIMESTAMP,  nSZ_UIIDSTAFFID);

        // convert the entire displayable string to a key (base 62).
        BaseConvert(szKey, nDEFAULT_BASE, nSZ_KEYTIMESTAMP, szTimestamp, 10);
        if (szKey[0] != '\0') { 
            // if the input string was not too big to convert
            BaseConvert(szKeyStaffId, nDEFAULT_BASE, nSZ_KEYSTAFFID, szStaffId, 10);

            if (szKeyStaffId[0] == '\0') { // input string is too long to be to converted...
                szKey[0] = '\0';           // so set the string to zero
            } else {
                strcat_s(szKey, nSZ_KEY + 1, szKeyStaffId);
            }
        }
    } catch (std::exception e) {
        cerr << "***** CAUGHT EXCEPTION! ***** : " << e.what() << endl;
        szKey[0] = '\0';
    }
}

//-----------------------------------------------------------------------------
//  Function:     GetUITimestampFromKey
//
//  Description:  Obtains a UI displayable timestamp from the generated key.
//
//  Inputs:       szKey - the previously generated key.
//
//  Outputs:      szUITimestamp - the timestamp in DB2 format (in base 10)
//                                An empty string on an error.
//                RETURNS - <none>
//-----------------------------------------------------------------------------
void WINAPI _export GetUITimestampFromKey(const char *szKey, char *szUITimestamp) {
    using namespace std;
    char szTimestampStr[nSZ_KEYTIMESTAMP + 1];
    double nTsVal;
    struct tm hNow;
    int nHSec;

    try {
        AssertTrace(strlen(szKey) == nSZ_KEY, "'%s' has an invalid key string length.", szKey);
        AssertTrace(AfxIsValidAddress(szUITimestamp, nSZ_UITIMESTAMP + 1), "Invalid address specified for szUITimestamp.");

        // Convert the key's timestamp segment to a number and then to date/time.
        StrCpyN(szTimestampStr, szKey, nSZ_KEYTIMESTAMP);
        
        // DRS: A C++ array decays to a pointer of the array's type. Love the syntax tho. :-)
        nTsVal = StrToDouble(szTimestampStr, BASE_62_SIZE, std::decay_t<double *>(&anPowVec62[0]));
        DoubleToTimestamp(nTsVal, &hNow, &nHSec);

        // Format the date/time in the default format of a DB2 timestamp.
        sprintf_s(szUITimestamp, nSZ_UITIMESTAMP + 2, "%04d-%02d-%02d-%02d.%02d.%02d.%06ld",
        hNow.tm_year + 1900, hNow.tm_mon + 1, hNow.tm_mday,
        hNow.tm_hour, hNow.tm_min, hNow.tm_sec, (long)((long)nHSec * 10000L));
    } catch (std::exception e) {
        cerr << "***** CAUGHT EXCEPTION! ***** : " << e.what() << std::endl;
        szUITimestamp[0] = '\0';
    }
}

//-----------------------------------------------------------------------------
//  Function:     GetPTimestampFromKey
//
//  Description:  Obtains a Performance timestamp from the generated key.
//
//  Inputs:       szKey - the previously generated key.
//
//  Outputs:      szUITimestamp - the timestamp in 10 byte format (hh:ss:mm:hshs)
//                                An empty string on an error.
//                RETURNS - <none>
//-----------------------------------------------------------------------------
void WINAPI _export GetPTimeStampFromKey(const char *szKey, char *szPTimestamp) {
    using namespace std;
    char szTimestampStr[nSZ_KEYTIMESTAMP + 1];
    double nTsVal;
    struct tm hNow;
    int nHSec;

    try {
        AssertTrace(strlen(szKey) == nSZ_KEY, "'%s' has an invalid key string length.", szKey);
        AssertTrace(AfxIsValidAddress(szPTimestamp, nSZ_PTIMESTAMP + 1), "Invalid address specified for szPTimestamp.");

        // Convert the key's timestamp segment to a number and then to date/time.
        StrCpyN(szTimestampStr, szKey, nSZ_KEYTIMESTAMP);
        nTsVal = StrToDouble(szTimestampStr, BASE_62_SIZE, const_cast<double*>(anPowVec62));

        DoubleToTimestamp(nTsVal, &hNow, &nHSec);

        // Format the date/time in the default format of a DB2 timestamp.
        sprintf_s(szPTimestamp, nSZ_PTIMESTAMP + 1, "%02d.%02d.%02d.%02d\0", hNow.tm_hour, hNow.tm_min, hNow.tm_sec, nHSec);
    } catch (std::exception e) {
        cerr << "***** CAUGHT EXCEPTION! ***** : " << e.what() << std::endl;
        szPTimestamp[0] = '\0';
    }
}

//-----------------------------------------------------------------------------
//                            Static Functions
//-----------------------------------------------------------------------------

// 
// DRS NOTE:
// "static" means something different in C++ from Java.
//
// In C++ a static function means that it's only visible to this compilation 
// unit (i.e., this .cpp file). The same is true of a static global variable
// (only visible to this .cpp file). 
//
// Static variables inside a function are the same as Java, meaning that the 
// variable applies to *all* calls of the function. Be careful of race 
// conditions, since C++ doesn't offer the "synchronized" keyword like Java.
//

//-----------------------------------------------------------------------------
//  Function:     BaseConvert
//
//  Description:  Converts a string of base 'a' to a string of base 'b'.
//                However, 'a' and 'b' can only be base 10 or 62.
//
//  Inputs:       nDstBase - the base of the destination string
//                szSrcStr - the source string
//                nSrcBase - the base of the source string
//
//  Outputs:      szDstStr - the destination string
//                RETURNS - <none>
//-----------------------------------------------------------------------------
static void BaseConvert(char *szDstStr, int nDstBase, int nDstWidth, const char *szSrcStr, int nSrcBase) {
    double nSrcVal = 0;

    // Error check the base values.
    AssertTrace(nSrcBase == 10 || nSrcBase == BASE_62_SIZE, "A source base of '%d' is invalid.", nSrcBase);
    AssertTrace(nDstBase == 10 || nDstBase == BASE_62_SIZE, "A destination base of '%d' is invalid.", nDstBase);

    // Convert the source string to a number.
    switch (nSrcBase) {
    case 10:
        nSrcVal = atof(szSrcStr);
        break;
    case BASE_62_SIZE:
        nSrcVal = StrToDouble(szSrcStr, nSrcBase, const_cast<double*>(anPowVec62));
        break;
    }

    // Convert the number to a string of specified base.
    switch (nDstBase) {
    case 10:
        DoubleToStrN(szDstStr, nDstWidth, nSrcVal, const_cast<double*>(anPowVec10));
        break;
    case BASE_62_SIZE:
        DoubleToStrN(szDstStr, nDstWidth, nSrcVal, const_cast<double*>(anPowVec62));
        break;
    }
}

//-----------------------------------------------------------------------------
//  Function:     CreateTimestampStr
//
//  Description:  Creates a UNIQUE timestamp string. The string is a base-62
//                representation of a timestamp number.
//
//  Inputs:       <none>
//  Outputs:      szTimestampStr - the timestamp string in base62 (null terminated)
//                RETURNS - a pointer to szTimestampStr
//  WARNING:      This algorithm does NOT scale on modern hardware and compilers.
//-----------------------------------------------------------------------------
static char * CreateTimestampStr(char *szTimestampStr) {
    SYSTEMTIME SystemTime;

    int iterations = 0;
    double nTimestamp = 0;
    thread_local double nPreviousTimestamp = 0;   // previous value - used for UNIQUENESS!!!

    while (TRUE) {
        ++iterations;
        GetLocalTime(&SystemTime);

        // Convert to a number.
        nTimestamp = TimestampToDouble(&SystemTime);

        // If the timestamp hasn't changed, stay in the loop.
        // Otherwise, break out since it is unique.
        if (g_win_tm == nullptr && nTimestamp == nPreviousTimestamp) {
            Yield();
        } else {
            break;
        }
    }

    nPreviousTimestamp = nTimestamp;  // save the current timestamp

    // Convert the timestamp number to a base62 string.
    return DoubleToStrN(szTimestampStr, nSZ_KEYTIMESTAMP, nTimestamp, const_cast<double *>(anPowVec62));
}

//-----------------------------------------------------------------------------
//  Function:     DoubleToStrN
//
//  Description:  Converts a double to a string (of given width) of base specified
//                by the power table.
//
//  Inputs:       szDstStr - the destination string
//                nDstStrWidth - number of digits in szDstStr (not including null)
//                nSrcVal - the value of the key
//                pnPowVec - the power vector for the destination base
//
//  Outputs:      szDstStr - the destination string, null terminated
//                RETURNS - a pointer to szDstStr
//-----------------------------------------------------------------------------
static char * DoubleToStrN(char *szDstStr, int nDstStrWidth, double nSrcVal, double *pnPowVec) {
    int i, nPower, nPad;
    double nFraction, nInteger;

    // Determine the number's largest power.
    nPower = 0;
    for (i = 0; nSrcVal >= pnPowVec[i]; i++, nPower++);

    // use the destination string width to left-pad the string.
    nPad = nDstStrWidth - nPower;

    if (nPad < 0) {
        // Input number is too big to be stored in a string of width nDestStrWidth.
        // Do not want to throw an exception here since the input number could have
        // been passed in from VB by a user entering a 19 character external key
        // that he thought was valid into a dialog box. So, return a null string
        // back to the caller that can be returned to the VB caller (of, say,
        // GetKeyFromUIIdentifier for example) so the VB code can display an error.

        szDstStr[0] = '\0';  // null terminate
    } else {
    
        for (i = 0; i < nPad; i++) {
            szDstStr[i] = acConvTbl[0];
        }

		// TODO: more efficient algorithm?
        for (i = 0; i < nPower; i++) {
            // Break down the number and convert the integer portion to a character.
            nFraction = modf(nSrcVal / pnPowVec[nPower - i - 1], &nInteger);
            szDstStr[i + nPad] = acConvTbl[(int)nInteger];
            nSrcVal -= (nInteger * pnPowVec[nPower - i - 1]);
        }

        szDstStr[nDstStrWidth] = '\0';  // null terminate
    }

    return szDstStr;
}

//-----------------------------------------------------------------------------
//  Function:     DoubleToTimestamp
//
//  Description:  Converts a double value to its appropriate timestamp
//                elements.
//
//  Inputs:       nTimestamp - the timestamp value.
//
//  Outputs:      phNow - a pointer to a struct tm that contains date/time info.
//                pnHSeconds - a pointer to the number of 1/100th of a second.
//                RETURNS - <none>
//-----------------------------------------------------------------------------
static void DoubleToTimestamp(double nTimestamp, struct tm *phNow, int *pnHSeconds) {
    memset(phNow, 0, sizeof(struct tm));  // initialize

    *pnHSeconds = (int)(nTimestamp / nSHIFT_HSECOND);        // 1/100 seconds
    nTimestamp -= ((double)*pnHSeconds * nSHIFT_HSECOND);    // strip it off

    phNow->tm_sec = (int)(nTimestamp / nSHIFT_SECOND);       // SECONDS
    nTimestamp   -= ((double)phNow->tm_sec * nSHIFT_SECOND); // strip it off

    phNow->tm_min = (int)(nTimestamp / nSHIFT_MINUTE);       // MINUTES
    nTimestamp   -= ((double)phNow->tm_min * nSHIFT_MINUTE); // strip it off

    phNow->tm_hour = (int)(nTimestamp / nSHIFT_HOUR);        // HOURS
    nTimestamp    -= ((double)phNow->tm_hour * nSHIFT_HOUR); // strip it off

    phNow->tm_mday = (int)(nTimestamp / nSHIFT_DAY);         // DAYS
    nTimestamp    -= ((double)phNow->tm_mday * nSHIFT_DAY);  // strip it off

    phNow->tm_mon = (int)(nTimestamp / nSHIFT_MONTH);        // MONTHS
    nTimestamp   -= ((double)phNow->tm_mon * nSHIFT_MONTH);  // strip it off

    phNow->tm_year = (int)(nTimestamp / nSHIFT_YEAR);        // YEARS
}

//-----------------------------------------------------------------------------
//  Function:     Itoa
//
//  Description:  Converts an integer in a given base to an ASCII string with
//                a specified width.
//
//  Inputs:       nInteger - the integer input
//                szOut - the output string
//                nOutputSize - number of digits that the output string
//                              should have (not including the null character);
//
//  Outputs:      szOut - the output string
//                RETURNS - a pointer to the output string
//-----------------------------------------------------------------------------
static char * Itoa(int nInteger, char *szOut, int nOutputSize) {
    sprintf_s(szOut, nSZ_KEYTIMESTAMP + 1, "%0*d", nOutputSize, nInteger);
    return szOut;
}

//-----------------------------------------------------------------------------
//  Function:     StrToDouble
//
//  Description:  Converts a string (in specified base) to a double.
//
//  Inputs:       szSrcStr - the key string (in base nSrcBase)
//                nSrcBase - the base of the key
//                pnPowVec - the power vector for the source base
//
//  Outputs:
//                RETURNS - the key value; -1 indicates an error.
//-----------------------------------------------------------------------------
static double StrToDouble(const char *szSrcStr, int nSrcBase, double *pnPowVec) {
    double nSrcVal = 0;
    int nLen = strlen(szSrcStr);
    int nPower;

    // Process all characters in the string.
    for (int i = 0; i < nLen; i++) {
        for (nPower = 0; nPower < nSrcBase; nPower++) {
            // Find the character in the conversion table and add to the value.
            if (acConvTbl[nPower] == szSrcStr[i]) {
                nSrcVal += (nPower * pnPowVec[nLen - i - 1]);
                break;
            }
        }

        if (nPower == nSrcBase) {
            // Character too big for the base! Bomb out!
            return -1;
        }
    }

    return nSrcVal;
}

//-----------------------------------------------------------------------------
//  Function:     TimestampToDouble
//
//  Description:  Converts the elements of a timestamp into a double.
//
//  Inputs:       hNow - the 'struct tm' of the current date/time
//                nHSeconds - the number of 1/100th of a second
//
//  Outputs:      <none>                
//  			  RETURNS - a double representing the date/time
//-----------------------------------------------------------------------------
static double TimestampToDouble(LPSYSTEMTIME lpTime) {
    double nTimestamp = 0;

    // PTS18039
    // do the 'shifting' and make a double out of the sum of the pieces.
    nTimestamp += (double)((double)(lpTime->wMilliseconds / 10) * nSHIFT_HSECOND);
    nTimestamp += (double)((double) lpTime->wSecond             * nSHIFT_SECOND);
    nTimestamp += (double)((double) lpTime->wMinute             * nSHIFT_MINUTE);
    nTimestamp += (double)((double) lpTime->wHour               * nSHIFT_HOUR);
    nTimestamp += (double)((double) lpTime->wDay                * nSHIFT_DAY);
    nTimestamp += (double)((double)(lpTime->wMonth - 1)         * nSHIFT_MONTH);
    nTimestamp += (double)((double)(lpTime->wYear - 1900)       * nSHIFT_YEAR);

    return nTimestamp;
}

//==========================
// TEST CASES:
//==========================

constexpr const std::size_t ITERS {3};

const std::array<char, BASE_62_SIZE> CARTESIAN_SEED {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};

// DRS: Not evaluated at compile time?? As President Trump would say, "Sad!" :-)
// constexpr const std::size_t CARTESIAN_SIZE {(std::size_t) std::pow((double)BASE_62_SIZE, (double)ITERS)};

constexpr const std::size_t CARTESIAN_SIZE {238328};

const auto build_base62_cartesian() {
	using namespace std;
	
    // Additional byte for null terminator.
    array<char[ITERS + 1], CARTESIAN_SIZE> retval;
    int row = 0;

    for ( const auto ic : CARTESIAN_SEED ) {
        for ( const auto jc : CARTESIAN_SEED ) {
            for ( const auto kc : CARTESIAN_SEED ) {
                retval[row][0] = ic;
                retval[row][1] = jc;
                retval[row][2] = kc;
                retval[row][3] = '\0';
                ++row;
            }
        }
    }
    
    return retval;
}

// void testWindowsDateTime() {
	// g_win_tm = make_unique<SYSTEMTIME>().get(); // heap
	// SYSTEMTIME sys_tm = SYSTEMTIME(); // stack
	// g_win_tm = &sys_tm;
	// g_win_tm->wYear         = 2012;
	// g_win_tm->wMonth        =   11;
	// g_win_tm->wDayOfWeek    =    2;  // DRS: not used in this unit.
	// g_win_tm->wDay          =   25;
	// g_win_tm->wHour         =   11;
	// g_win_tm->wMinute       =   15;
	// g_win_tm->wSecond       =   22;
	// g_win_tm->wMilliseconds =  187;
	// printWinTime(std::cout, *g_win_tm);
// }

//
// Test all possible staff id alpha-numeric combos.
//
// void testAllPossibleStaffIds() {
	// using namespace std;
	// using namespace std::chrono;
	// 
	// const auto STAFF_IDS = build_base62_cartesian();
	// if ( !s_staff_id.empty() ) {
	// 	cout << "\n\nDB2 timestamp format = hours.minutes.seconds.1/100 seconds\n\n";
	// 
	// 	const auto l_val = s_staff_id.c_str();
	// 	const array<char[ITERS + 1], 1> STAFF_IDS { {l_val} };
	// 	const array<char[ITERS + 1], 1> STAFF_IDS { {"0X5"} };
	// 
	// 	// cout << "\n\nCall MakeKeyAndTimeStamp: STAFF_IDS size=" << STAFF_IDS.size();
	// 	// cout << "\nTotal generated staff id's=" << STAFF_IDS.size() << endl;
	// 	
	// 	for (int i = 0; i < make_n_keys; i++) {
	// 		// for (const auto & staff_id : STAFF_IDS) {
	// 		const auto & staff_id = s_staff_id;
	// 		tm_str[0] = '\0';  // null out first char = dead string.
	// 		MakeKeyAndTimeStamp( &staff_id[0], &key[0], &tm_str[0] );
	// 		// cout << "\nstaff id=" << staff_id << ", ts_str=" << ts_str << ", key=" << key.get();
	// 
	// 		decomposeKey(key.get());
	// 
	// 		this_thread::sleep_for(milliseconds(11)); // Hundreds of a second ... OMG.
	// 	}
	// 	// }
	// 	cout << "\nDONE WITH MakeKeyAndTimeStamp" << endl;
	// }
// }

//==================================
// PUBLIC METHODS:
//==================================

// Decompose a key into staff id and timestamp.
//
// EXAMPLE OUTPUT:
// key:          5Y3vKVs0X5
// staff id:     0X5
// Timestamp (hr.min.sec.100th-sec):    11.15.22.18
// UI Timestamp: 2012-11-25-11.15.22.180000
//
void decomposeKey(const std::string & s_key) {
    using namespace std;

    char szStaffId[50]     = {0};
    char szUITimestamp[50] = {0};
    char szPTimestamp[12]  = {0};
    char timestampStr[50]  = {0};
    
    const char * k = s_key.c_str();
    
    GetStaffIdFromKey(k, szStaffId);
    GetUITimestampFromKey(k, szUITimestamp);
    GetPTimeStampFromKey(k, szPTimestamp);

#ifndef CWDS_BUILD_DLL
	cerr << "\n\nC++ DECOMPOSE KEY:"
         << "\nkey:                              "
         << s_key
         << "\nstaff id:                         "
         << szStaffId 
         << "\nTimestamp (hr.min.sec.1/100 sec): "
         << szPTimestamp
         << "\nUI Timestamp:                     "
         << szUITimestamp;
#endif
}

//
// Returns the *last* generated key. Only prints all others.
//
std::string generateKeys(const std::string & staff_id, int make_n_keys, const std::string & fixed_timestamp) {
    using namespace std;
    using namespace std::chrono;
    
    string retval;

    if ( !staff_id.empty() ) {

        char tm_str  [50] = {0};
        char key     [50] = {0};
        char ts_str  [50] = {0};

        for (int i = 0; i < make_n_keys; i++) {
            tm_str[0] = '\0';  // null out first char = dead string.

            if ( fixed_timestamp.empty() ) {
                MakeKeyAndTimeStamp( &staff_id[0], &key[0], &tm_str[0] );
            } else {
                copy( fixed_timestamp.begin(), fixed_timestamp.end(), &tm_str[0] );
            }
            
            if ( i > 0 ) {
                this_thread::sleep_for(milliseconds(11)); // DRS: Hundredths of a second ... OMG ...
            }
        }
        
        retval = key;
    }
    
    return retval;
}

std::string generateKey(const std::string & staff_id) {
    return generateKeys(staff_id, 1, "");
}

int showUsageAndExit(const char * program_nm) {
    printf("\n\nUSAGE:"\
        "\n\nOption: Generate Keys: -s <staff id> -n <# of keys> [-d datetime]"\
        "\nEX: %s -s 0X5 -n50"
        "\n\nOption: Decompose Key: -k <key>"\
        "\nEX: %s -k 5Y3vKVs0X5\n\n"
        , program_nm, program_nm);
    exit( EXIT_FAILURE );
}

//==========================
// MAIN:
//==========================

#ifdef CWDS_BUILD_DLL

/*
 * Class:     KeyJNI
 * Method:    generateKey
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_gov_ca_cwds_rest_util_jni_KeyJNI_generateKey(JNIEnv *env, jobject thisObj, jstring inJNIStr) {
	using namespace std;
	TRACE("C++: ENTER JNI generateKey!\n");

	if (NULL == inJNIStr) {
		TRACE("C++: generateKey(): NULL STAFF ID!");
		return NULL;
	}

	// Step 1: Convert the JNI String (jstring) into C-String (char*).
	const char *inCStr = env->GetStringUTFChars(inJNIStr, NULL);

	if (NULL == inCStr) {
		TRACE("C++: generateKey(): EMPTY STAFF ID!");
		return NULL;
	} else if (strlen(inCStr) != 3) {
		TRACE("C++: generateKey(): WRONG STAFF ID LENGTH!");
		return NULL;
	}

	for (size_t i = 0; i < 3; i++) {
		if (!is_base62( *(inCStr + i) )) {
			TRACE("C++: generateKey(): INVALID CHAR IN STAFF ID!");
			return NULL;
		}
	}

	// Step 2: Perform intended operations.
	TRACE("C++: received staff id = %s", inCStr);
	string outCppStr { generateKey(inCStr) };
	env->ReleaseStringUTFChars(inJNIStr, inCStr);  // release resources

	// Step 3: Convert the C++ string to C-string, then to JNI String (jstring) and return.
	return env->NewStringUTF(outCppStr.c_str());

	TRACE("C++: EXIT JNI generateKey!\n");
}

// Java "struct" for key details:
// private static final class KeyDetail {
//  public String key;
//     public String staffId;
//     public String UITimestamp;
//     public String PTimestamp;
// }

/*
 * Class:     KeyJNI
 * Method:    decomposeKey
 * Signature: (Ljava/lang/String;LKeyJNI/KeyDetail;)LKeyJNI/KeyDetail;
 */
JNIEXPORT void JNICALL Java_gov_ca_cwds_rest_util_jni_KeyJNI_decomposeKey(JNIEnv *env, jobject thisObj, jstring key, jobject key_detail) {
    using namespace std;
    TRACE("C++: ENTER decomposeKey()\n");
    if (NULL == key) {
        TRACE("C++: decomposeKey(): NULL KEY!");
        return;
    }
  
    // Get a reference to the KeyDetail class.
    jclass clazz = env->GetObjectClass(key_detail);

    // Step 1: Convert the JNI String (jstring) into C-String (char*).
    const char * k = env->GetStringUTFChars(key, NULL);
    if (NULL == k) {
        TRACE("C++: decomposeKey(): EMPTY KEY!");
        return;
    } else if (strlen(k) != 10) {
        TRACE("C++: decomposeKey(): WRONG KEY LENGTH!");
        return;
    }

    TRACE("C++: decomposeKey(): received key = %d", k);
    char szStaffId[50]     = {0};
    char szUITimestamp[50] = {0};
    char szPTimestamp[12]  = {0};
    
    GetStaffIdFromKey(k, szStaffId);
    GetUITimestampFromKey(k, szUITimestamp);
    GetPTimeStampFromKey(k, szPTimestamp);

	// TRACE << "\nC++: DECOMPOSE KEY:"
	// 	 << "\nkey:                              "
	// 	 << k
	// 	 << "\nstaff id:                         "
	// 	 << szStaffId 
	// 	 << "\nTimestamp (hr.min.sec.1/100 sec): "
	// 	 << szPTimestamp
	// 	 << "\nUI Timestamp:                     "
	// 	 << szUITimestamp
	// 	 << endl;

	// DRS: JNI magic starts here.

    // RETURN FIELDS:
    // KEY: 
    // Get the Field ID of the instance variable "key".
    jfieldID fidKey = env->GetFieldID(clazz, "key", "Ljava/lang/String;");
    if (NULL == fidKey) return;

    // Cast generic Object to Java String.
    jstring j_key = (jstring) env->GetObjectField(key_detail, fidKey);

    // Create a new C-string and assign to the JNI string.
    j_key = env->NewStringUTF(k);
    if (NULL == j_key) return;

    // Set the instance variables.
    env->SetObjectField(key_detail, fidKey, j_key);
    
    // JNI RULE:
    // If you call GetStringUTFChars(), then you must release created resource with ReleaseStringUTFChars();
    // I understand -- perhaps mistakenly -- that NewStringUTF() does not require manual release.
    
    // Play NICE in the JVM sandbox!
    // Check for null values. Don't kill the JVM by deferencing a wild pointer.
    
    // Staff ID:
    jfieldID fidStaffId = env->GetFieldID(clazz, "staffId", "Ljava/lang/String;");
    if (NULL == fidStaffId) return;
    jstring j_staffId = env->NewStringUTF(szStaffId);
    if (NULL == j_staffId) return;
    env->SetObjectField(key_detail, fidStaffId, j_staffId);

    // UITimestamp:
    jfieldID fidUITimestamp = env->GetFieldID(clazz, "UITimestamp", "Ljava/lang/String;");
    if (NULL == fidUITimestamp) return;
    jstring j_UITimestamp = env->NewStringUTF(szUITimestamp);
    if (NULL == j_UITimestamp) return;
    env->SetObjectField(key_detail, fidUITimestamp, j_UITimestamp);

    // PTimestamp:
    jfieldID fidPTimestamp = env->GetFieldID(clazz, "PTimestamp", "Ljava/lang/String;");
    if (NULL == fidPTimestamp) return;
    jstring j_PTimestamp = env->NewStringUTF(szPTimestamp);
    if (NULL == j_PTimestamp) return;
    env->SetObjectField(key_detail, fidPTimestamp, j_PTimestamp);

    // Release resources.
    env->ReleaseStringUTFChars(key, k);

    TRACE("C++: EXIT decomposeKey()\n");
}

#else

int main (int argc, char* argv[]) {
    using namespace std;
    using namespace std::chrono;

    //=================
    // CMD LINE OPTS
    //=================

    bool opt_verbose      = false;
    bool quiet_mode       = false;
    bool create_timestamp = false;
    unsigned int make_n_keys = 1;
    string s_datetime, s_staff_id, s_key;

    int opt;
    while ((opt = getopt(argc,argv,"tqvd:s:n:k:")) != EOF) {
        switch(opt) {
        case 'd': // ISO date string
            s_datetime = optarg;
            break;
        case 'n': // number of keys to make
            make_n_keys = atoi(optarg);
            break;
        case 'k': // rip key
            s_key = optarg;
            break;
        case 's': // staff id
            s_staff_id = optarg;
            break;
        case 'q': // quiet
            quiet_mode = true;
            break;
        case 'v': // verbose
            opt_verbose = true;
            break;
        case 't': // create timestamp
            create_timestamp = true;
            break;
        default:
            return showUsageAndExit( argv[0] );
        }
    }

    if (s_staff_id.empty() && s_key.empty() ) {
        return showUsageAndExit( argv[0] );
    }

    if ( !s_datetime.empty() ) {
        parse_iso8601_date(s_datetime.c_str());
    }

    auto tm_str = make_unique<char[]>(50);  // Is heap allocation necessary? Stack instead??
    auto key    = make_unique<char[]>(50);  // No need to set initial value; function call populates it.
    auto ts_str = CreateTimestampStr(&tm_str[0]);

    if (create_timestamp) {
        cout << "\nNEW TIMESTAMP: ts_str=" << ts_str << endl;
    }

    if ( !s_staff_id.empty() ) {
        generateKeys(s_staff_id, make_n_keys, "");
    }

    if ( !s_key.empty() ) {
        decomposeKey(s_key);
    }

    cout << '\n' << endl;

    return 0;
}

#endif





