
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNDatawedgeSpec.h"

@interface Datawedge : NSObject <NativeDatawedgeSpec>
#else
#import <React/RCTBridgeModule.h>

@interface Datawedge : NSObject <RCTBridgeModule>
#endif

@end
