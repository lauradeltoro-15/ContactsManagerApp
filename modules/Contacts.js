import {NativeModules} from 'react-native';
const {ContactsModule} = NativeModules;

export function createContact(contact) {
  if (!contact.name || !contact.phoneNumber) return;
  ContactsModule.createContact(contact.name, contact.phoneNumber);
}
