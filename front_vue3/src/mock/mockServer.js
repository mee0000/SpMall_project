import Mock from 'mockjs'
import banners from './banners.json';

Mock.mock("/mock/banner", {code:200, data: banners})