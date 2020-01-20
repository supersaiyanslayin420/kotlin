// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: compiler/ir/serialization.common/src/KotlinIr.proto

package org.jetbrains.kotlin.backend.common.serialization.proto;

public interface IrFileOrBuilder extends
    // @@protoc_insertion_point(interface_extends:org.jetbrains.kotlin.backend.common.serialization.proto.IrFile)
    org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder {

  /**
   * <code>repeated int32 declaration_id = 1;</code>
   *
   * <pre>
   *  repeated int64 declaration_id = 1;
   * </pre>
   */
  java.util.List<java.lang.Integer> getDeclarationIdList();
  /**
   * <code>repeated int32 declaration_id = 1;</code>
   *
   * <pre>
   *  repeated int64 declaration_id = 1;
   * </pre>
   */
  int getDeclarationIdCount();
  /**
   * <code>repeated int32 declaration_id = 1;</code>
   *
   * <pre>
   *  repeated int64 declaration_id = 1;
   * </pre>
   */
  int getDeclarationId(int index);

  /**
   * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.FileEntry file_entry = 2;</code>
   */
  boolean hasFileEntry();
  /**
   * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.FileEntry file_entry = 2;</code>
   */
  org.jetbrains.kotlin.backend.common.serialization.proto.FileEntry getFileEntry();

  /**
   * <code>repeated int32 fq_name = 3;</code>
   */
  java.util.List<java.lang.Integer> getFqNameList();
  /**
   * <code>repeated int32 fq_name = 3;</code>
   */
  int getFqNameCount();
  /**
   * <code>repeated int32 fq_name = 3;</code>
   */
  int getFqName(int index);

  /**
   * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall annotation = 4;</code>
   */
  java.util.List<org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall> 
      getAnnotationList();
  /**
   * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall annotation = 4;</code>
   */
  org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall getAnnotation(int index);
  /**
   * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall annotation = 4;</code>
   */
  int getAnnotationCount();

  /**
   * <code>repeated int32 explicitly_exported_to_compiler = 5;</code>
   */
  java.util.List<java.lang.Integer> getExplicitlyExportedToCompilerList();
  /**
   * <code>repeated int32 explicitly_exported_to_compiler = 5;</code>
   */
  int getExplicitlyExportedToCompilerCount();
  /**
   * <code>repeated int32 explicitly_exported_to_compiler = 5;</code>
   */
  int getExplicitlyExportedToCompiler(int index);

  /**
   * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.Actual actuals = 6;</code>
   */
  java.util.List<org.jetbrains.kotlin.backend.common.serialization.proto.Actual> 
      getActualsList();
  /**
   * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.Actual actuals = 6;</code>
   */
  org.jetbrains.kotlin.backend.common.serialization.proto.Actual getActuals(int index);
  /**
   * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.Actual actuals = 6;</code>
   */
  int getActualsCount();
}