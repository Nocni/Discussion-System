// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: discussion.proto

// Protobuf Java Version: 3.25.1
package rs.raf.grpc;

/**
 * Protobuf type {@code TopicsResponse}
 */
public final class TopicsResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:TopicsResponse)
    TopicsResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use TopicsResponse.newBuilder() to construct.
  private TopicsResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private TopicsResponse() {
    topics_ =
        com.google.protobuf.LazyStringArrayList.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new TopicsResponse();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return rs.raf.grpc.DiscussionServiceProto.internal_static_TopicsResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return rs.raf.grpc.DiscussionServiceProto.internal_static_TopicsResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            rs.raf.grpc.TopicsResponse.class, rs.raf.grpc.TopicsResponse.Builder.class);
  }

  public static final int TOPICS_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private com.google.protobuf.LazyStringArrayList topics_ =
      com.google.protobuf.LazyStringArrayList.emptyList();
  /**
   * <code>repeated string topics = 1;</code>
   * @return A list containing the topics.
   */
  public com.google.protobuf.ProtocolStringList
      getTopicsList() {
    return topics_;
  }
  /**
   * <code>repeated string topics = 1;</code>
   * @return The count of topics.
   */
  public int getTopicsCount() {
    return topics_.size();
  }
  /**
   * <code>repeated string topics = 1;</code>
   * @param index The index of the element to return.
   * @return The topics at the given index.
   */
  public java.lang.String getTopics(int index) {
    return topics_.get(index);
  }
  /**
   * <code>repeated string topics = 1;</code>
   * @param index The index of the value to return.
   * @return The bytes of the topics at the given index.
   */
  public com.google.protobuf.ByteString
      getTopicsBytes(int index) {
    return topics_.getByteString(index);
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    for (int i = 0; i < topics_.size(); i++) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, topics_.getRaw(i));
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    {
      int dataSize = 0;
      for (int i = 0; i < topics_.size(); i++) {
        dataSize += computeStringSizeNoTag(topics_.getRaw(i));
      }
      size += dataSize;
      size += 1 * getTopicsList().size();
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof rs.raf.grpc.TopicsResponse)) {
      return super.equals(obj);
    }
    rs.raf.grpc.TopicsResponse other = (rs.raf.grpc.TopicsResponse) obj;

    if (!getTopicsList()
        .equals(other.getTopicsList())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getTopicsCount() > 0) {
      hash = (37 * hash) + TOPICS_FIELD_NUMBER;
      hash = (53 * hash) + getTopicsList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static rs.raf.grpc.TopicsResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static rs.raf.grpc.TopicsResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static rs.raf.grpc.TopicsResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static rs.raf.grpc.TopicsResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static rs.raf.grpc.TopicsResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static rs.raf.grpc.TopicsResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static rs.raf.grpc.TopicsResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static rs.raf.grpc.TopicsResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static rs.raf.grpc.TopicsResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static rs.raf.grpc.TopicsResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static rs.raf.grpc.TopicsResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static rs.raf.grpc.TopicsResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(rs.raf.grpc.TopicsResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code TopicsResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:TopicsResponse)
      rs.raf.grpc.TopicsResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return rs.raf.grpc.DiscussionServiceProto.internal_static_TopicsResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return rs.raf.grpc.DiscussionServiceProto.internal_static_TopicsResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              rs.raf.grpc.TopicsResponse.class, rs.raf.grpc.TopicsResponse.Builder.class);
    }

    // Construct using rs.raf.grpc.TopicsResponse.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      topics_ =
          com.google.protobuf.LazyStringArrayList.emptyList();
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return rs.raf.grpc.DiscussionServiceProto.internal_static_TopicsResponse_descriptor;
    }

    @java.lang.Override
    public rs.raf.grpc.TopicsResponse getDefaultInstanceForType() {
      return rs.raf.grpc.TopicsResponse.getDefaultInstance();
    }

    @java.lang.Override
    public rs.raf.grpc.TopicsResponse build() {
      rs.raf.grpc.TopicsResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public rs.raf.grpc.TopicsResponse buildPartial() {
      rs.raf.grpc.TopicsResponse result = new rs.raf.grpc.TopicsResponse(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(rs.raf.grpc.TopicsResponse result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        topics_.makeImmutable();
        result.topics_ = topics_;
      }
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof rs.raf.grpc.TopicsResponse) {
        return mergeFrom((rs.raf.grpc.TopicsResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(rs.raf.grpc.TopicsResponse other) {
      if (other == rs.raf.grpc.TopicsResponse.getDefaultInstance()) return this;
      if (!other.topics_.isEmpty()) {
        if (topics_.isEmpty()) {
          topics_ = other.topics_;
          bitField0_ |= 0x00000001;
        } else {
          ensureTopicsIsMutable();
          topics_.addAll(other.topics_);
        }
        onChanged();
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();
              ensureTopicsIsMutable();
              topics_.add(s);
              break;
            } // case 10
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private com.google.protobuf.LazyStringArrayList topics_ =
        com.google.protobuf.LazyStringArrayList.emptyList();
    private void ensureTopicsIsMutable() {
      if (!topics_.isModifiable()) {
        topics_ = new com.google.protobuf.LazyStringArrayList(topics_);
      }
      bitField0_ |= 0x00000001;
    }
    /**
     * <code>repeated string topics = 1;</code>
     * @return A list containing the topics.
     */
    public com.google.protobuf.ProtocolStringList
        getTopicsList() {
      topics_.makeImmutable();
      return topics_;
    }
    /**
     * <code>repeated string topics = 1;</code>
     * @return The count of topics.
     */
    public int getTopicsCount() {
      return topics_.size();
    }
    /**
     * <code>repeated string topics = 1;</code>
     * @param index The index of the element to return.
     * @return The topics at the given index.
     */
    public java.lang.String getTopics(int index) {
      return topics_.get(index);
    }
    /**
     * <code>repeated string topics = 1;</code>
     * @param index The index of the value to return.
     * @return The bytes of the topics at the given index.
     */
    public com.google.protobuf.ByteString
        getTopicsBytes(int index) {
      return topics_.getByteString(index);
    }
    /**
     * <code>repeated string topics = 1;</code>
     * @param index The index to set the value at.
     * @param value The topics to set.
     * @return This builder for chaining.
     */
    public Builder setTopics(
        int index, java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      ensureTopicsIsMutable();
      topics_.set(index, value);
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>repeated string topics = 1;</code>
     * @param value The topics to add.
     * @return This builder for chaining.
     */
    public Builder addTopics(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      ensureTopicsIsMutable();
      topics_.add(value);
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>repeated string topics = 1;</code>
     * @param values The topics to add.
     * @return This builder for chaining.
     */
    public Builder addAllTopics(
        java.lang.Iterable<java.lang.String> values) {
      ensureTopicsIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, topics_);
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>repeated string topics = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearTopics() {
      topics_ =
        com.google.protobuf.LazyStringArrayList.emptyList();
      bitField0_ = (bitField0_ & ~0x00000001);;
      onChanged();
      return this;
    }
    /**
     * <code>repeated string topics = 1;</code>
     * @param value The bytes of the topics to add.
     * @return This builder for chaining.
     */
    public Builder addTopicsBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      ensureTopicsIsMutable();
      topics_.add(value);
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:TopicsResponse)
  }

  // @@protoc_insertion_point(class_scope:TopicsResponse)
  private static final rs.raf.grpc.TopicsResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new rs.raf.grpc.TopicsResponse();
  }

  public static rs.raf.grpc.TopicsResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<TopicsResponse>
      PARSER = new com.google.protobuf.AbstractParser<TopicsResponse>() {
    @java.lang.Override
    public TopicsResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<TopicsResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<TopicsResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public rs.raf.grpc.TopicsResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

