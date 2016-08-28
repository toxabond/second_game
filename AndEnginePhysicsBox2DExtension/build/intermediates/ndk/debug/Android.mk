LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := andenginephysicsbox2dextension
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Android.mk \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Application.mk \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Android.mk \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Body.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\CircleShape.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Collision\b2BroadPhase.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Collision\b2CollideCircle.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Collision\b2CollidePolygon.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Collision\b2Collision.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Collision\b2Distance.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Collision\b2DynamicTree.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Collision\b2TimeOfImpact.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Collision\Shapes\b2CircleShape.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Collision\Shapes\b2PolygonShape.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Common\b2BlockAllocator.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Common\b2Math.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Common\b2Settings.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Common\b2StackAllocator.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Contact.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\ContactImpulse.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\DistanceJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\b2Body.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\b2ContactManager.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\b2Fixture.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\b2Island.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\b2World.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\b2WorldCallbacks.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\Contacts\b2CircleContact.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\Contacts\b2Contact.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\Contacts\b2ContactSolver.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\Contacts\b2PolygonAndCircleContact.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\Contacts\b2PolygonContact.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\Contacts\b2TOISolver.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\Joints\b2DistanceJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\Joints\b2FrictionJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\Joints\b2GearJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\Joints\b2Joint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\Joints\b2LineJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\Joints\b2MouseJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\Joints\b2PrismaticJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\Joints\b2PulleyJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\Joints\b2RevoluteJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Dynamics\Joints\b2WeldJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Fixture.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\FrictionJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\GearJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Joint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\LineJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Manifold.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\MouseJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\PolygonShape.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\PrismaticJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\PulleyJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\RevoluteJoint.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\Shape.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\Box2D\World.cpp \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\build.bat \
	C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni\build.sh \

LOCAL_C_INCLUDES += C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\main\jni
LOCAL_C_INCLUDES += C:\AndEngine\MyApplication\AndEnginePhysicsBox2DExtension\src\debug\jni

include $(BUILD_SHARED_LIBRARY)
